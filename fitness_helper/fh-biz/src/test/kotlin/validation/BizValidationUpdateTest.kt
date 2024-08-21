package validation

import models.FhCommand
import validation.*
import validationDescriptionEmpty
import validationDescriptionSymbols
import validationDescriptionTrim
import validationIdEmpty
import validationIdFormat
import validationIdTrim
import validationLockFormat
import validationLockTrim
import kotlin.test.Test

class BizValidationUpdateTest : BaseBizValidationTest() {
    override val command = FhCommand.UPDATE

    @Test fun correctName() = validationTitleCorrect(command, processor)
    @Test fun trimName() = validationTitleTrim(command, processor)
    @Test fun emptyName() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsName() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)
}