package com.fitness.helper.biz.stubs

import FhContext
import FhCorSettings
import ICorChainDsl
import NONE
import com.fitness.helper.common.models.*
import com.fitness.helper.stubs.FhExerciseStub
import kotlinx.datetime.Instant
import models.FhState
import stubs.FhStubs
import worker


fun ICorChainDsl<FhContext>.stubCreateSuccess(title: String, corSettings: FhCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания упражнения
    """.trimIndent()

    on { stubCase == FhStubs.SUCCESS && state == FhState.RUNNING }
    handle {
        state = FhState.FINISHING
        val stub = FhExerciseStub.prepareResult {
            name = exerciseRequest.name.takeIf { it.isNotBlank() } ?: "Default Name"
            description = exerciseRequest.description.takeIf { it.isNotBlank() } ?: "Default Description"
            muscleGroup = exerciseRequest.muscleGroup.takeIf { it != FhMuscleGroup.NONE } ?: FhMuscleGroup.ARMS
            lastPerformed = exerciseRequest.lastPerformed.takeIf { it != Instant.NONE } ?: Instant.fromEpochMilliseconds(0)
            nextReminder = exerciseRequest.nextReminder.takeIf { it != Instant.NONE } ?: Instant.fromEpochMilliseconds(0)
            addedOn = exerciseRequest.addedOn.takeIf { it != Instant.NONE } ?: Instant.fromEpochMilliseconds(0)
        }
        exerciseResponse = stub

        // Логирование для проверки
        println("Stub response: $exerciseResponse")
    }
}