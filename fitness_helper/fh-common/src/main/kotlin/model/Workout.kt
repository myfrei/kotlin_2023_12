package com.fitness.helper.models

data class Workout(
    var id: WorkoutId = WorkoutId.NONE,
    var name: String = "",
    var description: String = "",
    var duration: Int = 0,
    var exercises: MutableList<Exercise> = mutableListOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Workout()
    }
}

@JvmInline
value class WorkoutId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = WorkoutId("")
    }
}

data class Exercise(
    var id: ExerciseId = ExerciseId.NONE,
    var name: String = "",
    var description: String = "",
    var muscleGroup: String = "",
    var videoUrl: String = ""
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Exercise()
    }
}

@JvmInline
value class ExerciseId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = ExerciseId("")
    }
}