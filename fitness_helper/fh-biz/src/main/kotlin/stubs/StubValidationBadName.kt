package com.fitness.helper.biz.stubs

import FhContext
import ICorChainDsl
import helpers.fail
import models.FhError
import models.FhState
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubValidationBadName(title: String) = worker {
    this.title = title
    on { stubCase == FhStubs.BAD_NAME && state == FhState.RUNNING }
    handle {
        state = FhState.FAILING
        errors.add(
            FhError(
                code = "validation-name-empty",
                group = "validation",
                field = "name",
                message = "Name must not be empty"
            )
        )
    }
}