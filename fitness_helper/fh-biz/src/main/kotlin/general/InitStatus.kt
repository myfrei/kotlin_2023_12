package com.fitness.helper.biz.general

import FhContext
import ICorChainDsl
import models.FhState
import worker

fun ICorChainDsl<FhContext>.initStatus(title: String) = worker() {
    this.title = title
    this.description = """
        Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
    """.trimIndent()
    on { state == FhState.NONE }
    handle { state = FhState.RUNNING }
}
