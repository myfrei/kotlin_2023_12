package validation

import FhContext
import kotlinx.coroutines.test.runTest
import models.FhExerciseFilter
import models.FhCommand
import models.FhState
import models.FhWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest : BaseBizValidationTest() {
    override val command = FhCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = FhContext(
            command = command,
            state = FhState.NONE,
            workMode = FhWorkMode.TEST,
            exerciseFilterRequest = FhExerciseFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(FhState.FAILING, ctx.state)
    }
}