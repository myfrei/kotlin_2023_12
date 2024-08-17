package ru.otus.otuskotlin.fh.repo.inmemory

import NONE
import com.fitness.helper.common.models.FhMuscleGroup
import kotlinx.datetime.Instant
import models.*


data class ExerciseEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val visibility: String? = null,
    val muscleGroup: String? = null,
    val importance: String? = null,
    val lock: String? = null,
    val lastPerformed: String? = null,
    val nextReminder: String? = null,
    val addedOn: String? = null,
    val append: String? = null
) {
    constructor(model: FhExercise) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != FhVisibility.NONE }?.name,
        muscleGroup = model.muscleGroup.takeIf { it != FhMuscleGroup.NONE }?.name,
        importance = model.importance.takeIf { it != FhExerciseImportance.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() },
        lastPerformed = model.lastPerformed.toString().takeIf { it.isNotBlank() },
        nextReminder = model.nextReminder.toString().takeIf { it.isNotBlank() },
        addedOn = model.addedOn.toString().takeIf { it.isNotBlank() },
        append = model.append.toString().takeIf { it.isNotBlank() },
    )

    private fun getInstant(timeString: String): Instant {
        return try {
            Instant.parse(timeString)
        } catch (e: Exception) {
            Instant.NONE
        }
    }

    fun toInternal() = FhExercise(
        id = id?.let { FhExerciseId(it) } ?: FhExerciseId.NONE,
        name = name ?: "",
        description = description ?: "",
        ownerId = ownerId?.let { FhUserId(it) } ?: FhUserId.NONE,
        visibility = visibility?.let { FhVisibility.valueOf(it) } ?: FhVisibility.NONE,
        muscleGroup = muscleGroup?.let { FhMuscleGroup.valueOf(it) } ?: FhMuscleGroup.NONE,
        importance = importance?.let { FhExerciseImportance.valueOf(it) } ?: FhExerciseImportance.NONE,
        lock = lock?.let { FhExerciseLock(it) } ?: FhExerciseLock.NONE,
        lastPerformed = lastPerformed?.let { getInstant(it) } ?: Instant.NONE,
        nextReminder = nextReminder?.let { getInstant(it) } ?: Instant.NONE,
        addedOn = addedOn?.let { getInstant(it) } ?: Instant.NONE,
        append = append?.let { getInstant(it) } ?: Instant.NONE,
    )
}