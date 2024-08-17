package com.fitness.helper.biz.stubs

import FhContext
import FhCorSettings
import ICorChainDsl
import NONE
import kotlinx.datetime.Instant
import com.fitness.helper.stubs.FhExerciseStub
import models.FhExerciseImportance
import models.FhState
import models.FhVisibility
import stubs.FhStubs
import worker

fun ICorChainDsl<FhContext>.stubUpdateSuccess(title: String, corSettings: FhCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для обновления упражнения
    """.trimIndent()

    on { stubCase == FhStubs.SUCCESS && state == FhState.RUNNING }
    handle {
        state = FhState.FINISHING
        val stub = FhExerciseStub.prepareResult {
            this.id = exerciseRequest.id
            this.name = exerciseRequest.name.takeIf { it.isNotBlank() } ?: this.name
            this.description = exerciseRequest.description.takeIf { it.isNotBlank() } ?: this.description
            this.visibility = exerciseRequest.visibility.takeIf { it != FhVisibility.NONE } ?: this.visibility
            this.importance = exerciseRequest.importance.takeIf { it != FhExerciseImportance.NONE } ?: this.importance
            this.append = exerciseRequest.append.takeIf { it != Instant.NONE } ?: this.append
        }
        exerciseResponse = stub
    }
}