package config

import FhCorSettings
import com.fitness.helper.app.common.IFhAppSettings
import com.fitness.helper.biz.FhExerciseProcessor

data class FhAppSettings(
    override val corSettings: FhCorSettings,
    override val processor: FhExerciseProcessor,
): IFhAppSettings