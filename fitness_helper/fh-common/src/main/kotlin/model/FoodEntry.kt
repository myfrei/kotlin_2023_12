package com.fitness.helper.models

import kotlinx.datetime.LocalDateTime

data class FoodEntry(
    var id: FoodEntryId = FoodEntryId.NONE,
    var userId: UserId = UserId.NONE,
    var foodName: String = "",
    var calories: Double = 0.0,
    var proteins: Double = 0.0,
    var fats: Double = 0.0,
    var carbohydrates: Double = 0.0,
    var timestamp: LocalDateTime = LocalDateTime.NONE
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = FoodEntry()
    }
}

@JvmInline
value class FoodEntryId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = FoodEntryId("")
    }
}

val LocalDateTime.Companion.NONE: LocalDateTime
    get() = LocalDateTime.parse("0001-01-01T00:00:00")