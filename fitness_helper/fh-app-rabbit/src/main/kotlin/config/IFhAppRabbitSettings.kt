package config

import com.fitness.helper.app.common.IFhAppSettings

interface IFhAppRabbitSettings : IFhAppSettings {
    val rabbit: RabbitConfig
    val controllersConfig: RabbitExchangeConfiguration
}