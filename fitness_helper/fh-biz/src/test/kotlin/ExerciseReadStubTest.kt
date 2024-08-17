import kotlinx.coroutines.test.runTest
import com.fitness.helper.biz.FhExerciseProcessor
import com.fitness.helper.stubs.FhExerciseStub
import models.*
import stubs.FhStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseReadStubTest {

    private val processor = FhExerciseProcessor()
    val id = FhExerciseId("123")

    @Test
    fun read() = runTest {

        val ctx = FhContext(
            command = FhCommand.READ,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.SUCCESS,
            exerciseRequest = FhExercise(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (FhExerciseStub.get()) {
            assertEquals(id, ctx.exerciseResponse.id)
            assertEquals(name, ctx.exerciseResponse.name)
            assertEquals(description, ctx.exerciseResponse.description)
            assertEquals(visibility, ctx.exerciseResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = FhContext(
            command = FhCommand.READ,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_ID,
            exerciseRequest = FhExercise(),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = FhContext(
            command = FhCommand.READ,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.DB_ERROR,
            exerciseRequest = FhExercise(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = FhContext(
            command = FhCommand.READ,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_NAME,
            exerciseRequest = FhExercise(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}