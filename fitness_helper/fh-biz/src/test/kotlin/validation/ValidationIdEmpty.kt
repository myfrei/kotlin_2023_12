import com.fitness.helper.biz.FhExerciseProcessor
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertEquals
import kotlin.test.assertContains

fun validationIdEmpty(command: FhCommand, processor: FhExerciseProcessor) = runTest {
    val ctx = FhContext(
        command = command,
        state = FhState.NONE,
        workMode = FhWorkMode.TEST,
        exerciseRequest = FhExercise(
            id = FhExerciseId(""),
            name = "abc",
            description = "abc",
            visibility = FhVisibility.VISIBLE_PUBLIC,
            lock = FhExerciseLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(FhState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}