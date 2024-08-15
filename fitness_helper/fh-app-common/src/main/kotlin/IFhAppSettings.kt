package com.fitness.helper.app.common

import FhCorSettings
import com.fitness.helper.biz.FhExerciseProcessor

interface IFhAppSettings {
    val processor: FhExerciseProcessor
    val corSettings: FhCorSettings
}