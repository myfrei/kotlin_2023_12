package validation

import models.FhCommand
import validationDescriptionEmpty
import validationDescriptionTrim
import validationDescriptionSymbols
import kotlin.test.Test

class BizValidationCreateTest : BaseBizValidationTest() {
    override val command: FhCommand = FhCommand.CREATE

    @Test fun correctName() = validationTitleCorrect(command, processor)
    @Test fun trimName() = validationTitleTrim(command, processor)
    @Test fun emptyName() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsName() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)
}