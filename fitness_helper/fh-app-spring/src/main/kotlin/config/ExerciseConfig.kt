package com.fitness.helper.app.spring.config

import FhCorSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fitness.helper.biz.FhExerciseProcessor
import config.FhAppSettings

@Suppress("unused")
@Configuration
class ExerciseConfig {
    @Bean
    fun processor(corSettings: FhCorSettings) = FhExerciseProcessor(corSettings = corSettings)

    @Bean
    fun corSettings(): FhCorSettings = FhCorSettings(
        loggerProvider = "test" // Замените на реальный провайдер логгирования
    )

    @Bean
    fun appSettings(
        corSettings: FhCorSettings,
        processor: FhExerciseProcessor,
    ) = FhAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}