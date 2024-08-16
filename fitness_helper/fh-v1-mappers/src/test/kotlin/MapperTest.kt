package com.fitness.helper.mappers

import FhContext
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.Test
import com.fitness.helper.common.models.*
import fromTransport
import models.*
import ru.white.api.models.*
import stubs.FhStubs
import toTransportExercise
import kotlin.test.assertEquals

class MapperTest {

    private fun getTestInstant(timeString: String): Instant {
        val ldt: LocalDateTime = LocalDateTime.parse(timeString)
        return ldt.toInstant(TimeZone.UTC)
    }

    @Test
    fun fromTransport() {
        val req = ExerciseCreateRequest(
            debug = ExerciseDebug(
                mode = ExerciseRequestDebugMode.STUB,
                stub = ExerciseRequestDebugStubs.SUCCESS,
            ),
            exercise = ExerciseCreateObject(
                name = "Push-up",
                description = "Basic push-up exercise",
                muscleGroup = MuscleGroup.ARMS,
                lastPerformed = "2024-05-20T08:09:10Z",
                nextReminder = "2024-05-20T08:09:10Z",
                addedOn = "2024-05-20T08:09:10Z"
            ),
        )

        val context = FhContext()
        context.fromTransport(req)

        assertEquals(FhStubs.SUCCESS, context.stubCase)
        assertEquals(FhWorkMode.STUB, context.workMode)
        assertEquals("Push-up", context.exerciseRequest.name)
        assertEquals("Basic push-up exercise", context.exerciseRequest.description)
        assertEquals(FhMuscleGroup.ARMS, context.exerciseRequest.muscleGroup)
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.exerciseRequest.lastPerformed)
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.exerciseRequest.nextReminder)
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.exerciseRequest.addedOn)
    }

    @Test
    fun toTransport() {
        val context = FhContext(
            requestId = FhRequestId("1234"),
            command = FhCommand.CREATE,
            exerciseResponse = FhExercise(
                name = "Push-up",
                description = "Basic push-up exercise",
                muscleGroup = FhMuscleGroup.ARMS,
                lastPerformed = getTestInstant("2024-05-20T08:09:10"),
                nextReminder = getTestInstant("2024-05-20T08:09:10"),
                addedOn = getTestInstant("2024-05-20T08:09:10")
            ),
            errors = mutableListOf(
                FhError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = FhState.RUNNING,
        )

        val req = context.toTransportExercise() as ExerciseCreateResponse

        assertEquals("Push-up", req.exercise?.name)
        assertEquals("Basic push-up exercise", req.exercise?.description)
        assertEquals(MuscleGroup.ARMS, req.exercise?.muscleGroup)
        assertEquals("2024-05-20T08:09:10Z", req.exercise?.lastPerformed)
        assertEquals("2024-05-20T08:09:10Z", req.exercise?.nextReminder)
        assertEquals("2024-05-20T08:09:10Z", req.exercise?.addedOn)

        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.message)
    }
}