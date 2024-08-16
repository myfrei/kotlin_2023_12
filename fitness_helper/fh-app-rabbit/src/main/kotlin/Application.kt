import config.FhAppSettings
import config.RabbitConfig
import kotlinx.coroutines.runBlocking
import mappers.fromArgs

fun main(vararg args: String) = runBlocking {
    val appSettings = FhAppSettings(
        rabbit = RabbitConfig.fromArgs(*args),
        corSettings = FhCorSettings()
    )
    val app = RabbitApp(appSettings = appSettings, this)
    app.start()
}