package com.fitness.helper

import ru.white.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = ExerciseCreateResponse(
        responseType = "create",
        result = ResponseResult.SUCCESS,
        exercise = ExerciseResponseObject(
            id = "1",
            name = "Push-up",
            description = "A basic push-up exercise",
            muscleGroup = MuscleGroup.ARMS,
            lastPerformed = "2024-01-01T10:00:00Z",
            nextReminder = "2024-01-08T10:00:00Z",
            addedOn = "2024-01-01T09:00:00Z"
        ),
        errors = listOf()
    )

    @Test
    fun serialize() {
        val json = apiV1ResponseSerialize(response)

        assertContains(json, Regex("\"name\":\\s*\"Push-up\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1ResponseSerialize(response)
        val obj = apiV1ResponseDeserialize<ExerciseCreateResponse>(json)

        assertEquals(response, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"exercise": null, "responseType": "create", "result": "success", "errors": []}
        """.trimIndent()
        val obj = apiV1ResponseDeserialize<ExerciseCreateResponse>(jsonString)

        assertEquals(null, obj.exercise)
    }
}