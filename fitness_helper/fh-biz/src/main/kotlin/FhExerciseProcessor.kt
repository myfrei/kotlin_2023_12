package com.fitness.helper.biz


import FhContext
import FhCorSettings
import com.fitness.helper.stubs.FhExerciseStub
import models.FhState

@Suppress("unused", "RedundantSuspendModifier")
class FhExerciseProcessor(val corSettings: FhCorSettings) {
    suspend fun exec(ctx: FhContext) {
        ctx.exerciseResponse = FhExerciseStub.get()
        ctx.exercisesResponse = FhExerciseStub.prepareExerciseList("arm workout").toMutableList()
        ctx.state = FhState.RUNNING
    }
}