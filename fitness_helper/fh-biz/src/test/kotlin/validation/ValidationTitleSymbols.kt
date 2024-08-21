package validation

import FhContext
import com.fitness.helper.biz.FhExerciseProcessor
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

fun validationTitleSymbols(command: FhCommand, processor: FhExerciseProcessor) = runTest {
    val ctx = FhContext(
        command = command,
        state = FhState.NONE,
        workMode = FhWorkMode.TEST,
        exerciseRequest = FhExercise(
            id = FhExerciseId("123"),
            name = "!@#$%^&*(),.{}",
            description = "abc",
            visibility = FhVisibility.VISIBLE_PUBLIC,
            lock = FhExerciseLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(FhState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}