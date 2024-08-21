package models

data class FhExerciseFilter(
    var searchString: String = "",
    var ownerId: FhUserId = FhUserId.NONE,
) {
    fun deepCopy(): FhExerciseFilter = copy()

    fun isEmpty(): Boolean = this == NONE

    companion object {
        val NONE = FhExerciseFilter()
    }
}