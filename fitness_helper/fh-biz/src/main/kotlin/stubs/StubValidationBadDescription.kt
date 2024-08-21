package com.fitness.helper.biz.stubs

import FhContext
import ICorChainDsl
import helpers.fail
import models.FhError
import models.FhState
import stubs.FhStubs
import worker


fun ICorChainDsl<FhContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для описания упражнения
    """.trimIndent()
    on { stubCase == FhStubs.BAD_DESCRIPTION && state == FhState.RUNNING }
    handle {
        fail(
            FhError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}