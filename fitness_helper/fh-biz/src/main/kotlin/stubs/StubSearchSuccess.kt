package com.fitness.helper.biz.stubs


import FhContext
import FhCorSettings
import ICorChainDsl
import com.fitness.helper.stubs.FhExerciseStub
import models.FhState
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubSearchSuccess(title: String, corSettings: FhCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска упражнений
    """.trimIndent()
    on { stubCase == FhStubs.SUCCESS && state == FhState.RUNNING }
    handle {
        state = FhState.FINISHING
        exercisesResponse.addAll(FhExerciseStub.prepareExerciseList(exerciseFilterRequest.searchString))
    }
}