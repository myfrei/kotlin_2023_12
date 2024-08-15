package com.fitness.helper.app.spring.controllers

import org.springframework.web.bind.annotation.*
import com.fitness.helper.app.common.controllerHelper
import config.FhAppSettings
import fromTransport
import ru.white.api.models.*
import toTransportExercise

import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("exercise")
class ExerciseController(
    private val appSettings: FhAppSettings
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: ExerciseCreateRequest): ExerciseCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun read(@RequestBody request: ExerciseReadRequest): ExerciseReadResponse =
        process(appSettings, request = request, this::class, "read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun update(@RequestBody request: ExerciseUpdateRequest): ExerciseUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun delete(@RequestBody request: ExerciseDeleteRequest): ExerciseDeleteResponse =
        process(appSettings, request = request, this::class, "delete")

    @PostMapping("search")
    suspend fun search(@RequestBody request: ExerciseSearchRequest): ExerciseSearchResponse =
        process(appSettings, request = request, this::class, "search")

    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: FhAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            {
                fromTransport(request)
            },
            { toTransportExercise() as R },
        )
    }
}