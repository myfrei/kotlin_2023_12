import models.*

abstract class BaseInitExercises(private val op: String): IInitObjects<FhExercise> {
    open val lockOld: FhExerciseLock = FhExerciseLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: FhExerciseLock = FhExerciseLock("20000000-0000-0000-0000-000000000009")
    fun createInitTestModel(
        suf: String,
        ownerId: FhUserId = FhUserId("owner-123"),
        lock: FhExerciseLock = lockOld
    ) = FhExercise(
        id = FhExerciseId("task-repo-$op-$suf"),
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = FhVisibility.VISIBLE_TO_OWNER,
        lock = lock
    )
}
