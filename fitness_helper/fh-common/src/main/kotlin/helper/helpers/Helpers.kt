package com.fitness.helper.helpers

import com.fitness.helper.models.FitnessError

fun Throwable.toFitnessError(
    group: String = "exceptions",
    message: String = this.message ?: "",
    code: String = "unknown"
) = FitnessError(
    group = group,
    message = message,
    code = code
)