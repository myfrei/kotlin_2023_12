package ru.otus.otuskotlin.fh.repo.postgresql

import NONE
import kotlinx.datetime.Instant
import models.FhExercise
import models.FhExerciseId
import models.FhExerciseLock
import models.FhUserId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class ExerciseTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val name = text(SqlFields.NAME).nullable()
    val description = text(SqlFields.DESCRIPTION).nullable()
    val owner = text(SqlFields.OWNER_ID)
    val visibility = visibilityEnumeration(SqlFields.VISIBILITY)
    val lock = text(SqlFields.LOCK)

    val importance = importanceEnumeration(SqlFields.IMPORTANCE)
    val muscleGroup = muscleGroupEnumeration(SqlFields.MUSCLE_GROUP)

    val lastPerformed = text(SqlFields.LAST_PERFORMED).nullable()
    val nextReminder = text(SqlFields.NEXT_REMINDER).nullable()
    val addedOn = text(SqlFields.ADDED_ON).nullable()
    val append = text(SqlFields.APPEND).nullable()

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = FhExercise(
        id = FhExerciseId(res[id].toString()),
        name = res[name] ?: "",
        description = res[description] ?: "",
        ownerId = FhUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = FhExerciseLock(res[lock]),
        importance = res[importance],
        muscleGroup = res[muscleGroup],
        lastPerformed = res[lastPerformed]?.let { Instant.parse(it) } ?: Instant.NONE,
        nextReminder = res[nextReminder]?.let { Instant.parse(it) } ?: Instant.NONE,
        addedOn = res[addedOn]?.let { Instant.parse(it) } ?: Instant.NONE,
        append = res[append]?.let { Instant.parse(it) } ?: Instant.NONE
    )

    fun to(it: UpdateBuilder<*>, exercise: FhExercise, randomUuid: () -> String) {
        it[id] = exercise.id.takeIf { it != FhExerciseId.NONE }?.asString() ?: randomUuid()
        it[name] = exercise.name
        it[description] = exercise.description
        it[owner] = exercise.ownerId.asString()
        it[visibility] = exercise.visibility
        it[lock] = exercise.lock.takeIf { it != FhExerciseLock.NONE }?.asString() ?: randomUuid()

        it[importance] = exercise.importance
        it[muscleGroup] = exercise.muscleGroup
        it[lastPerformed] = exercise.lastPerformed.toString()
        it[nextReminder] = exercise.nextReminder.toString()
        it[addedOn] = exercise.addedOn.toString()
        it[append] = exercise.append.toString()
    }
}