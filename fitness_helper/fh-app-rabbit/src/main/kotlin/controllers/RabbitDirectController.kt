package controllers

import FhContext
import com.fitness.helper.apiV1Mapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import com.fitness.helper.app.common.controllerHelper
import config.FhAppSettings
import fromTransport
import helpers.asFhError
import models.FhState
import ru.white.api.models.IRequest
import toTransportExercise


class RabbitDirectController(
    private val appSettings: FhAppSettings,
) : RabbitProcessorBase(
    rabbitConfig = appSettings.rabbit,
    exchangeConfig = appSettings.controllersConfig,
) {
    override suspend fun Channel.processMessage(message: Delivery) {
        appSettings.controllerHelper(
            {
                val req = apiV1Mapper.readValue(message.body, IRequest::class.java)
                fromTransport(req)
            },
            {
                val res = toTransportExercise()
                apiV1Mapper.writeValueAsBytes(res).also {
                    basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
                }
            },
        )
    }

    override fun Channel.onError(e: Throwable, delivery: Delivery) {
        val context = FhContext()
        e.printStackTrace()
        context.state = FhState.FAILING
        context.errors.add(e.asFhError())
        val response = context.toTransportExercise()
        apiV1Mapper.writeValueAsBytes(response).also {
            basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
        }
    }
}