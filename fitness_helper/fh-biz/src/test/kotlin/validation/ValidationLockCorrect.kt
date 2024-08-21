package validation

import FhContext
import com.fitness.helper.biz.FhExerciseProcessor
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: FhCommand, processor: FhExerciseProcessor) = runTest {
    val ctx = FhContext(
        command = command,
        state = FhState.NONE,
        workMode = FhWorkMode.TEST,
        exerciseRequest = FhExercise(
            id = FhExerciseId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            visibility = FhVisibility.VISIBLE_PUBLIC,
            lock = FhExerciseLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(FhState.FAILING, ctx.state)
}