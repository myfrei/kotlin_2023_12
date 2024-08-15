package com.fitness.helper.stubs


import NONE
import com.fitness.helper.common.models.*
import kotlinx.datetime.Instant
import models.FhExercise
import models.FhExerciseId
import models.FhExercisePermissionClient
import models.FhUserId

object FhExerciseStubEntities {
    val EXERCISE_PUSHUP_ENTITY1: FhExercise
        get() = FhExercise(
            id = FhExerciseId("001"),
            name = "Push-up",
            description = "Standard push-up exercise",
            muscleGroup = FhMuscleGroup.ARMS,
            lastPerformed = Instant.NONE,
            nextReminder = Instant.NONE,
            addedOn = Instant.NONE,
            ownerId = FhUserId("test_user_1"),
            permissionsClient = mutableSetOf(
                FhExercisePermissionClient.READ,
                FhExercisePermissionClient.UPDATE,
                FhExercisePermissionClient.DELETE,
                FhExercisePermissionClient.REMIND,
            )
        )
}