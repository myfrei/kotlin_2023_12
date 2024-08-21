package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import worker

fun ICorChainDsl<FhContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { exerciseValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}