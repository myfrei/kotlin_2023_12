package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import models.FhState
import worker

fun ICorChainDsl<FhContext>.finishExerciseFilterValidation(title: String) = worker {
    this.title = title
    on { state == FhState.RUNNING }
    handle {
        exerciseFilterValidated = exerciseFilterValidating
    }
}