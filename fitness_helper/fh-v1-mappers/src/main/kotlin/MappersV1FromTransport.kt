package com.fitness.helper.mappers

import com.fitness.helper.api.v1.models.*
import com.fitness.helper.models.*
import com.fitness.helper.FitnessContext
import java.math.BigDecimal

fun FitnessContext.fromTransport(request: IRequest) = when (request) {
    is UserRegistrationRequest -> fromTransport(request)
    is UserLoginRequest -> fromTransport(request)
    is FoodEntryRequest -> fromTransport(request)

    else -> throw UnknownRequestClass(request::class.java)
}

fun FitnessContext.fromTransport(request: UserRegistrationRequest) {
    state = FitnessState.RUNNING
    userRequest = request.toInternal()
    workMode = request.debug?.mode.transportToWorkMode()
}

fun FitnessContext.fromTransport(request: UserLoginRequest) {
    state = FitnessState.RUNNING
    userRequest = User().apply {
        email = request.email
        password = request.password
    }
    workMode = request.debug?.mode.transportToWorkMode()
}

fun FitnessContext.fromTransport(request: FoodEntryRequest) {
    state = FitnessState.RUNNING
    foodEntryRequest = request.toInternal()
    workMode = request.debug?.mode.transportToWorkMode()
}

private fun UserRegistrationRequest.toInternal() = User(
    fullName = this.fullName,
    email = this.email,
    password = this.password,
    dateOfBirth = this.dateOfBirth,
    gender = this.gender,
    height = this.height,
    weight = this.weight,
    activityLevel = this.activityLevel
)

private fun FoodEntryRequest.toInternal() = FoodEntry(
    userId = UserId(this.userId.toString()),
    foodName = this.foodName,
    calories = this.calories,
    proteins = this.proteins,
    fats = this.fats,
    carbohydrates = this.carbohydrates,
    timestamp = java.time.Instant.now().toString()
)

private fun String?.toBigDecimal(): BigDecimal = this?.let { BigDecimal(it) } ?: BigDecimal.ZERO

private fun NoteRequestDebugMode?.transportToWorkMode(): FitnessWorkMode {
    return if (this?.name.equals(FitnessWorkMode.TEST.name, ignoreCase = true)) {
        FitnessWorkMode.TEST
    } else {
        FitnessWorkMode.PROD
    }
}