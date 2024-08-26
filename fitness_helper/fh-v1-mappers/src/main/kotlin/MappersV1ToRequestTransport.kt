package ru.otus.otuskotlin.fh.mappers

import com.fitness.helper.common.models.FhMuscleGroup
import models.*
import ru.white.api.models.*

fun FhExercise.toTransportCreate() = ExerciseCreateObject(
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    muscleGroup = muscleGroup.toTransportExercise(),
    lastPerformed = lastPerformed.toString(),
    nextReminder = nextReminder.toString(),
    addedOn = addedOn.toString(),
)

fun FhExercise.toTransportRead() = ExerciseReadObject(
    id = id.takeIf { it != FhExerciseId.NONE }?.asString(),
)

fun FhExercise.toTransportUpdate() = ExerciseUpdateObject(
    id = id.takeIf { it != FhExerciseId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    muscleGroup = muscleGroup.toTransportExercise(),
    lastPerformed = lastPerformed.toString(),
    nextReminder = nextReminder.toString(),
    addedOn = addedOn.toString(),
    lock = lock.takeIf { it != FhExerciseLock.NONE }?.asString(),
)

fun FhExercise.toTransportDelete() = ExerciseDeleteObject(
    id = id.takeIf { it != FhExerciseId.NONE }?.asString(),
    lock = lock.takeIf { it != FhExerciseLock.NONE }?.asString(),
)

private fun FhMuscleGroup.toTransportExercise(): MuscleGroup? = when (this) {
    FhMuscleGroup.CHEST -> MuscleGroup.CHEST
    FhMuscleGroup.BACK -> MuscleGroup.BACK
    FhMuscleGroup.LEGS -> MuscleGroup.LEGS
    FhMuscleGroup.ARMS -> MuscleGroup.ARMS
    FhMuscleGroup.SHOULDERS -> MuscleGroup.SHOULDERS
    FhMuscleGroup.ABS -> MuscleGroup.ABS
    FhMuscleGroup.NONE -> null
}