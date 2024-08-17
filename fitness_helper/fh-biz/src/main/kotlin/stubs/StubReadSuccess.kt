package com.fitness.helper.biz.stubs

import FhContext
import FhCorSettings
import ICorChainDsl
import com.fitness.helper.stubs.FhExerciseStub
import models.FhState
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubReadSuccess(title: String, corSettings: FhCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения упражнения
    """.trimIndent()
    on { stubCase == FhStubs.SUCCESS && state == FhState.RUNNING }
    handle {
        state = FhState.FINISHING
        val stub = FhExerciseStub.prepareResult {
            exerciseRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        exerciseResponse = stub
    }
}