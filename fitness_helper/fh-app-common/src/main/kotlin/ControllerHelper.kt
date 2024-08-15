package com.fitness.helper.app.common

import FhContext
import helpers.asFhError
import kotlinx.datetime.Clock
import models.FhState

suspend inline fun <T> IFhAppSettings.controllerHelper(
    crossinline getRequest: suspend FhContext.() -> Unit,
    crossinline toResponse: suspend FhContext.() -> T,
): T {
    val ctx = FhContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        processor.exec(ctx)
        ctx.toResponse()
    } catch (e: Throwable) {
        ctx.state = FhState.FAILING
        ctx.errors.add(e.asFhError())
        processor.exec(ctx)
        ctx.toResponse()
    }
}