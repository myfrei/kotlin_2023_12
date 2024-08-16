package controllers

import com.rabbitmq.client.*
import kotlinx.coroutines.*
import config.RabbitConfig
import config.RabbitExchangeConfiguration
import kotlin.coroutines.CoroutineContext

abstract class RabbitProcessorBase @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val rabbitConfig: RabbitConfig,
    override val exchangeConfig: RabbitExchangeConfiguration,
    private val dispatcher: CoroutineContext = Dispatchers.IO.limitedParallelism(1) + Job(),
) : IRabbitMqController {
    private var conn: Connection? = null
    private var chan: Channel? = null
    override suspend fun process() = withContext(dispatcher) {
        ConnectionFactory().apply {
            host = rabbitConfig.host
            port = rabbitConfig.port
            username = rabbitConfig.user
            password = rabbitConfig.password
        }.newConnection().use { connection ->
            conn = connection
            connection.createChannel().use { channel ->
                chan = channel
                val deliveryCallback = channel.getDeliveryCallback()
                val cancelCallback = getCancelCallback()
                channel.describeAndListen(deliveryCallback, cancelCallback)
            }
        }
    }

    protected abstract suspend fun Channel.processMessage(message: Delivery)

    protected abstract fun Channel.onError(e: Throwable, delivery: Delivery)

    private fun Channel.getDeliveryCallback(): DeliverCallback = DeliverCallback { _, delivery: Delivery ->
        runBlocking {
            kotlin.runCatching {
                val deliveryTag: Long = delivery.envelope.deliveryTag
                processMessage(delivery)
                this@getDeliveryCallback.basicAck(deliveryTag, false)
            }.onFailure {
                onError(it, delivery)
            }
        }
    }

    private fun getCancelCallback() = CancelCallback {}

    private suspend fun Channel.describeAndListen(
        deliverCallback: DeliverCallback,
        cancelCallback: CancelCallback
    ) {
        withContext(Dispatchers.IO) {
            exchangeDeclare(exchangeConfig.exchange, exchangeConfig.exchangeType)
            queueDeclare(exchangeConfig.queue, false, false, false, null)
            queueBind(exchangeConfig.queue, exchangeConfig.exchange, exchangeConfig.keyIn)
            basicConsume(
                exchangeConfig.queue,
                false,
                exchangeConfig.consumerTag,
                deliverCallback,
                cancelCallback
            )

            while (isOpen) {
                runCatching {
                    delay(100)
                }.recover { }
            }
        }
    }

    override fun close() {
        chan?.takeIf { it.isOpen }?.run {
            basicCancel(exchangeConfig.consumerTag)
            close()
        }
        conn?.takeIf { it.isOpen }?.run {
            close()
        }
    }
}