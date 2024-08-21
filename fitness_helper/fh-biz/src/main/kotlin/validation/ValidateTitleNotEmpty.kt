package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import worker


// проверяем, что название упражнения не пустое
fun ICorChainDsl<FhContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { exerciseValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}