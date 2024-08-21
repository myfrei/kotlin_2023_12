import com.fitness.helper.biz.FhExerciseProcessor
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdTrim(command: FhCommand, processor: FhExerciseProcessor) = runTest {
    val ctx = FhContext(
        command = command,
        state = FhState.NONE,
        workMode = FhWorkMode.TEST,
        exerciseRequest = FhExercise(
            id = FhExerciseId(" \n\t 123-234-abc-ABC \n\t "),
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