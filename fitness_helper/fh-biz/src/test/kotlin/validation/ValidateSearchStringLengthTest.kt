package validation

import FhContext
import com.fitness.helper.biz.validation.validateSearchStringLength
import kotlinx.coroutines.test.runTest
import models.FhExerciseFilter
import models.FhState
import rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseFilterValidating = FhExerciseFilter(searchString = ""))
        chain.exec(ctx)
        assertEquals(FhState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseFilterValidating = FhExerciseFilter(searchString = "  "))
        chain.exec(ctx)
        assertEquals(FhState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseFilterValidating = FhExerciseFilter(searchString = "12"))
        chain.exec(ctx)
        assertEquals(FhState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseFilterValidating = FhExerciseFilter(searchString = "123"))
        chain.exec(ctx)
        assertEquals(FhState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseFilterValidating = FhExerciseFilter(searchString = "12".repeat(51)))
        chain.exec(ctx)
        assertEquals(FhState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain<FhContext> {
            validateSearchStringLength("Проверка длины строки поиска")
        }.build()
    }
}