package com.fitness.helper.biz.stubs

import FhContext
import ICorChainDsl
import helpers.fail
import models.FhError
import models.FhState
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == FhStubs.DB_ERROR && state == FhState.RUNNING }
    handle {
        fail(
            FhError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}