package com.fitness.helper

import com.fitness.helper.models.*
import kotlinx.datetime.Instant

data class FitnessContext(
    var state: FitnessState = FitnessState.NONE,
    val errors: MutableList<FitnessError> = mutableListOf(),
    var workMode: FitnessWorkMode = FitnessWorkMode.PROD,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var userRequest: User = User(),
    var foodEntryRequest: FoodEntry = FoodEntry(),

    var userResponse: User = User(),
    var foodEntriesResponse: MutableList<FoodEntry> = mutableListOf(),
    var workoutResponse: Workout = Workout(),
    var workoutsResponse: MutableList<Workout> = mutableListOf(),
    var bmiResponse: BMIResult = BMIResult()
)

@JvmInline
value class RequestId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = RequestId("")
    }
}

enum class FitnessState {
    NONE,
    RUNNING,
    FAILING,
    FINISHING,
}

enum class FitnessWorkMode {
    PROD,
    TEST,
}