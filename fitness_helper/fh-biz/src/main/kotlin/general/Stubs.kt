package com.fitness.helper.biz.general

import FhContext
import ICorChainDsl
import chain
import models.FhState
import models.FhWorkMode


fun ICorChainDsl<FhContext>.stubs(title: String, block: ICorChainDsl<FhContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == FhWorkMode.STUB && state == FhState.RUNNING }
}