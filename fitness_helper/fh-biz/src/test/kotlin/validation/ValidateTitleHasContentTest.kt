package validation

import FhContext
import com.fitness.helper.biz.validation.validateTitleHasContent
import kotlinx.coroutines.test.runTest
import models.FhExercise
import models.FhState
import rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleHasContentTest {
    @Test
    fun emptyString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseValidating = FhExercise(name = ""))
        chain.exec(ctx)
        assertEquals(FhState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseValidating = FhExercise(name = "12!@#$%^&*()_+-="))
        chain.exec(ctx)
        assertEquals(FhState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-name-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = FhContext(state = FhState.RUNNING, exerciseValidating = FhExercise(name = "Ж"))
        chain.exec(ctx)
        assertEquals(FhState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain<FhContext> {
            validateTitleHasContent("Проверка наличия контента в названии")
        }.build()
    }
}