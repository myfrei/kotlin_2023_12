package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import chain
import models.FhState
import worker


fun ICorChainDsl<FhContext>.validation(block: ICorChainDsl<FhContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == FhState.RUNNING }
}
