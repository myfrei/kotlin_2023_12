package config

import FhCorSettings
import com.fitness.helper.app.common.IFhAppSettings
import com.fitness.helper.biz.FhExerciseProcessor

data class FhAppSettings(
    override val corSettings: FhCorSettings = FhCorSettings(),
    override val processor: FhExerciseProcessor = FhExerciseProcessor(corSettings),
    override val rabbit: RabbitConfig = RabbitConfig(),
    override val controllersConfig: RabbitExchangeConfiguration = RabbitExchangeConfiguration.NONE,
) : IFhAppSettings, IFhAppRabbitSettings