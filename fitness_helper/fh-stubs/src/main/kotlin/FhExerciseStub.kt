package com.fitness.helper.stubs


import com.fitness.helper.stubs.FhExerciseStubEntities.EXERCISE_PUSHUP_ENTITY1
import models.FhExercise
import models.FhExerciseId

object FhExerciseStub {
    fun get(): FhExercise = EXERCISE_PUSHUP_ENTITY1.copy()

    fun prepareResult(block: FhExercise.() -> Unit): FhExercise = get().apply(block)

    fun prepareExerciseList(filter: String) = listOf(
        fhExercise("ex-001", filter),
        fhExercise("ex-002", filter),
        fhExercise("ex-003", filter),
        fhExercise("ex-004", filter),
        fhExercise("ex-005", filter),
        fhExercise("ex-006", filter),
    )

    private fun fhExercise(id: String, filter: String) =
        fhExercise(EXERCISE_PUSHUP_ENTITY1, id = id, filter = filter)

    private fun fhExercise(base: FhExercise, id: String, filter: String) = base.copy(
        id = FhExerciseId(id),
        name = "$filter $id",
        description = "desc $filter $id",
    )
}