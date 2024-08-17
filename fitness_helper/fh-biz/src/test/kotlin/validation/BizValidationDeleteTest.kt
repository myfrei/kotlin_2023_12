package validation

import models.FhCommand
import validationIdEmpty
import validationIdFormat
import validationIdTrim
import validationLockFormat
import validationLockTrim
import kotlin.test.Test

class BizValidationDeleteTest : BaseBizValidationTest() {
    override val command = FhCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)
}