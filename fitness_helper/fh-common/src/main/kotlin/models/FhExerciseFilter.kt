package models

data class FhExerciseFilter (
    var searchString: String = "",
    var ownerId: FhUserId = FhUserId.NONE,
)