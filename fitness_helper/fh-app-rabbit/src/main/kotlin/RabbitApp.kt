import kotlinx.coroutines.*
import config.FhAppSettings
import controllers.IRabbitMqController
import controllers.RabbitDirectController
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalCoroutinesApi::class)
class RabbitApp(
    appSettings: FhAppSettings,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) : AutoCloseable {
    private val controllers: List<IRabbitMqController> = listOf(
        RabbitDirectController(appSettings)
    )
    private val runFlag = AtomicBoolean(true)

    fun start() {
        runFlag.set(true)
        controllers.forEach {
            scope.launch(
                Dispatchers.IO.limitedParallelism(1) + CoroutineName("thread-${it.exchangeConfig.consumerTag}")
            ) {
                while (runFlag.get()) {
                    try {
                        it.process()
                    } catch (e: RuntimeException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun close() {
        runFlag.set(false)
        controllers.forEach { it.close() }
        scope.cancel()
    }
}