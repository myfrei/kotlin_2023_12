package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import models.FhExerciseLock
import worker

fun ICorChainDsl<FhContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в FhExerciseLock для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { exerciseValidating.lock != FhExerciseLock.NONE && !exerciseValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = exerciseValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}