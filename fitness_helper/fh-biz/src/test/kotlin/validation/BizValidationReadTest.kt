package validation

import models.FhCommand
import validationIdEmpty
import validationIdFormat
import validationIdTrim
import kotlin.test.Test

class BizValidationReadTest : BaseBizValidationTest() {
    override val command = FhCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
}