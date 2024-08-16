package com.fitness.helper

import ru.white.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = ExerciseCreateRequest(
        debug = ExerciseDebug(
            mode = ExerciseRequestDebugMode.STUB,
            stub = ExerciseRequestDebugStubs.BAD_NAME
        ),
        exercise = ExerciseCreateObject(
            name = "Push-up",
            description = "A basic push-up exercise",
            muscleGroup = MuscleGroup.ARMS
        )
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)

        assertContains(json, Regex("\"name\":\\s*\"Push-up\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1RequestSerialize(request)
        val obj = apiV1RequestDeserialize<ExerciseCreateRequest>(json)

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
        {"requestType": "create", "exercise": null}
    """.trimIndent()
        val obj = apiV1RequestDeserialize<ExerciseCreateRequest>(jsonString)

        assertEquals(null, obj.exercise)
    }
}