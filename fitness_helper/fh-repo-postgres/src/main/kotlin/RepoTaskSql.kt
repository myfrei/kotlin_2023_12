package ru.otus.otuskotlin.fh.repo.postgresql

import IRepoExerciseInitializable
import com.benasher44.uuid.uuid4
import helpers.asFhError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.FhExercise
import models.FhExerciseId
import models.FhExerciseLock
import models.FhUserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import repo.*


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
class RepoExerciseSql constructor(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() },
) : IRepoExercise, IRepoExerciseInitializable {
    private val exerciseTable = ExerciseTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        exerciseTable.deleteAll()
    }

    private fun saveObj(exercise: FhExercise): FhExercise = transaction(conn) {
        val res = exerciseTable
            .insert {
                to(it, exercise, randomUuid)
            }
            .resultedValues
            ?.map { exerciseTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbExerciseResponse): IDbExerciseResponse =
        transactionWrapper(block) { DbExerciseResponseErr(it.asFhError()) }

    override fun save(exercises: Collection<FhExercise>): Collection<FhExercise> = exercises.map { saveObj(it) }
    override suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse = transactionWrapper {
        DbExerciseResponseOk(saveObj(rq.exercise))
    }

    private fun read(id: FhExerciseId): IDbExerciseResponse {
        val res = exerciseTable.selectAll().where {
            exerciseTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbExerciseResponseOk(exerciseTable.from(res))
    }

    override suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: FhExerciseId,
        lock: FhExerciseLock,
        block: (FhExercise) -> IDbExerciseResponse
    ): IDbExerciseResponse =
        transactionWrapper {
            if (id == FhExerciseId.NONE) return@transactionWrapper errorEmptyId

            val current = exerciseTable.selectAll().where { exerciseTable.id eq id.asString() }
                .singleOrNull()
                ?.let { exerciseTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }

    override suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse = update(rq.exercise.id, rq.exercise.lock) {
        exerciseTable.update({ exerciseTable.id eq rq.exercise.id.asString() }) {
            to(it, rq.exercise.copy(lock = FhExerciseLock(randomUuid())), randomUuid)
        }
        read(rq.exercise.id)
    }

    override suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse = update(rq.id, rq.lock) {
        exerciseTable.deleteWhere { id eq rq.id.asString() }
        DbExerciseResponseOk(it)
    }

    override suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse =
        transactionWrapper({
            val res = exerciseTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != FhUserId.NONE) {
                        add(exerciseTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.nameFilter.isNotBlank()) {
                        add(
                            (exerciseTable.name like "%${rq.nameFilter}%")
                                    or (exerciseTable.description like "%${rq.nameFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbExercisesResponseOk(data = res.map { exerciseTable.from(it) })
        }, {
            DbExercisesResponseErr(it.asFhError())
        })
}