package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import models.FhExerciseId
import worker

fun ICorChainDsl<FhContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в FhExerciseId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { exerciseValidating.id != FhExerciseId.NONE && !exerciseValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = exerciseValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}