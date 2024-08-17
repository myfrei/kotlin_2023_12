package com.fitness.helper.biz.stubs

import FhContext
import ICorChainDsl
import helpers.fail
import models.FhError
import models.FhState
import worker

fun ICorChainDsl<FhContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == FhState.RUNNING }
    handle {
        fail(
            FhError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}