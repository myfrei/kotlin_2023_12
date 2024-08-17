package validation

import FhCorSettings
import com.fitness.helper.biz.FhExerciseProcessor
import models.FhCommand

abstract class BaseBizValidationTest {
    protected abstract val command: FhCommand
    private val settings by lazy { FhCorSettings() }
    protected val processor by lazy { FhExerciseProcessor(settings) }
}