package ru.otus.otuskotlin.fh.repo.inmemory

import IRepoExerciseInitializable
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import models.*
import repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class ExerciseRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IRepoExercise, IRepoExerciseInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, ExerciseEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(exercises: Collection<FhExercise>) = exercises.map { exercise ->
        val entity = ExerciseEntity(exercise)
        require(entity.id != null)
        cache.put(entity.id, entity)
        exercise
    }

    override suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse = try {
        val key = randomUuid()
        val exercise = rq.exercise.copy(id = FhExerciseId(key), lock = FhExerciseLock(randomUuid()))
        val entity = ExerciseEntity(exercise)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbExerciseResponseOk(exercise)
    } catch (e: Exception) {
        DbExerciseResponseErr(listOf(FhError(message = e.message ?: "Unknown error")))
    }

    override suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return try {
            val key = rq.id.takeIf { it != FhExerciseId.NONE }?.asString() ?: return DbExerciseResponseErr(listOf(FhError(message = "Empty ID")))
            mutex.withLock {
                cache.get(key)
                    ?.let {
                        DbExerciseResponseOk(it.toInternal())
                    } ?: DbExerciseResponseErr(listOf(FhError(message = "Not Found", field = "id", code = "repo-not-found")))
            }
        } catch (e: Exception) {
            DbExerciseResponseErr(listOf(FhError(message = e.message ?: "Unknown error")))
        }
    }

    override suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse {
        return try {
            val rqExercise = rq.exercise
            val id = rqExercise.id.takeIf { it != FhExerciseId.NONE } ?: return DbExerciseResponseErr(listOf(FhError(message = "Empty ID")))
            val key = id.asString()
            val oldLock = rqExercise.lock.takeIf { it != FhExerciseLock.NONE } ?: return DbExerciseResponseErr(listOf(FhError(message = "Empty Lock", field = "lock", code = "repo-empty-lock")))

            mutex.withLock {
                val oldExercise = cache.get(key)?.toInternal()
                when {
                    oldExercise == null -> DbExerciseResponseErr(listOf(FhError(message = "Not Found", field = "id", code = "repo-not-found")))
                    oldExercise.lock == FhExerciseLock.NONE -> DbExerciseResponseErr(listOf(FhError(message = "Empty Lock", field = "lock", code = "repo-empty-lock")))
                    oldExercise.lock != oldLock -> DbExerciseResponseErr(listOf(FhError(message = "Concurrency Error", field = "lock", code = "repo-concurrency-error")))
                    else -> {
                        val newExercise = rqExercise.copy(lock = FhExerciseLock(randomUuid()))
                        val entity = ExerciseEntity(newExercise)
                        cache.put(key, entity)
                        DbExerciseResponseOk(newExercise)
                    }
                }
            }
        } catch (e: Exception) {
            DbExerciseResponseErr(listOf(FhError(message = e.message ?: "Unknown error")))
        }
    }

    override suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return try {
            val id = rq.id.takeIf { it != FhExerciseId.NONE } ?: return DbExerciseResponseErr(listOf(FhError(message = "Empty ID")))
            val key = id.asString()
            val oldLock = rq.lock.takeIf { it != FhExerciseLock.NONE } ?: return DbExerciseResponseErr(listOf(FhError(message = "Empty Lock", field = "lock", code = "repo-empty-lock")))

            mutex.withLock {
                val oldExercise = cache.get(key)?.toInternal()
                when {
                    oldExercise == null -> DbExerciseResponseErr(listOf(FhError(message = "Not Found", field = "id", code = "repo-not-found")))
                    oldExercise.lock == FhExerciseLock.NONE -> DbExerciseResponseErr(listOf(FhError(message = "Empty Lock", field = "lock", code = "repo-empty-lock")))
                    oldExercise.lock != oldLock -> DbExerciseResponseErr(listOf(FhError(message = "Concurrency Error", field = "lock", code = "repo-concurrency-error")))
                    else -> {
                        cache.invalidate(key)
                        DbExerciseResponseOk(oldExercise)
                    }
                }
            }
        } catch (e: Exception) {
            DbExerciseResponseErr(listOf(FhError(message = e.message ?: "Unknown error")))
        }
    }

    override suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse = try {
        val result: List<FhExercise> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != FhUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.nameFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbExercisesResponseOk(result)
    } catch (e: Exception) {
        DbExercisesResponseErr(listOf(FhError(message = e.message ?: "Unknown error")))
    }
}