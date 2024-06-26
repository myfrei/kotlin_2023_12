package com.fitness.helper.models

import java.math.BigDecimal
import kotlinx.datetime.LocalDate

data class User(
    var id: UserId = UserId.NONE,
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    var dateOfBirth: String = "",
    var gender: String = "",
    var height: BigDecimal = BigDecimal.ZERO,
    var weight: BigDecimal = BigDecimal.ZERO,
    var activityLevel: String = "",
    var progress: MutableList<WeightProgress> = mutableListOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = User()
    }
}

@JvmInline
value class UserId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = UserId("")
    }
}

data class WeightProgress(
    var date: LocalDate = LocalDate.NONE,
    var weight: BigDecimal = BigDecimal.ZERO
)

val LocalDate.Companion.NONE: LocalDate
    get() = LocalDate.parse("0001-01-01")