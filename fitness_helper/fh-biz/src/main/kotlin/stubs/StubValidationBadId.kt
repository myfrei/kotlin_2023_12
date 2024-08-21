package com.fitness.helper.biz.stubs

import FhContext
import ICorChainDsl
import helpers.fail
import models.FhError
import models.FhState
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для идентификатора упражнения
    """.trimIndent()
    on { stubCase == FhStubs.BAD_ID && state == FhState.RUNNING }
    handle {
        fail(
            FhError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}