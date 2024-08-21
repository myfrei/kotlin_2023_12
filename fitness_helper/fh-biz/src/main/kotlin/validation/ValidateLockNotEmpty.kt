package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import worker


fun ICorChainDsl<FhContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { exerciseValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}