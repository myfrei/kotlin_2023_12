import com.fitness.helper.biz.FhExerciseProcessor
import com.fitness.helper.stubs.FhExerciseStub
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = FhExerciseStub.get()

fun validationDescriptionTrim(command: FhCommand, processor: FhExerciseProcessor) = runTest {
    val ctx = FhContext(
        command = command,
        state = FhState.NONE,
        workMode = FhWorkMode.TEST,
        exerciseRequest = FhExercise(
            id = stub.id,
            name = "abc",
            description = " \n\tabc \n\t",
            visibility = FhVisibility.VISIBLE_PUBLIC,
            lock = FhExerciseLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(FhState.FAILING, ctx.state)
    assertEquals("abc", ctx.exerciseValidated.description)
}