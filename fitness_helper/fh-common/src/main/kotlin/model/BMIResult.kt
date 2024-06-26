package com.fitness.helper.models

data class BMIResult(
    var bmi: Double = 0.0,
    var status: String = "",
    var recommendations: String = ""
)