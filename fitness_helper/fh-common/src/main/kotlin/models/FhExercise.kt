package models

import kotlinx.datetime.Instant
import NONE
import com.fitness.helper.common.models.FhMuscleGroup

data class FhExercise(
    var id: FhExerciseId = FhExerciseId.NONE,
    var name: String = "",
    var description: String = "",
    var muscleGroup: FhMuscleGroup = FhMuscleGroup.NONE,
    var lastPerformed: Instant = Instant.NONE,
    var nextReminder: Instant = Instant.NONE,
    var addedOn: Instant = Instant.NONE,
    var ownerId: FhUserId = FhUserId.NONE,
    var visibility: FhVisibility = FhVisibility.NONE,
    var lock: FhExerciseLock = FhExerciseLock.NONE,
    val permissionsClient: MutableSet<FhExercisePermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = FhExercise()
    }
}