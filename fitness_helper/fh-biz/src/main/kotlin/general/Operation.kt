package com.fitness.helper.biz.general

import FhContext
import ICorChainDsl
import chain
import models.FhCommand
import models.FhState


fun ICorChainDsl<FhContext>.operation(
    title: String,
    command: FhCommand,
    block: ICorChainDsl<FhContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == FhState.RUNNING }
}