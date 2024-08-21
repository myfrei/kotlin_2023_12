import kotlinx.coroutines.test.runTest
import com.fitness.helper.biz.FhExerciseProcessor
import com.fitness.helper.stubs.FhExerciseStub
import models.*
import stubs.FhStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseDeleteStubTest {

    private val processor = FhExerciseProcessor()
    val id = FhExerciseId("123")

    @Test
    fun delete() = runTest {

        val ctx = FhContext(
            command = FhCommand.DELETE,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.SUCCESS,
            exerciseRequest = FhExercise(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = FhExerciseStub.get()
        assertEquals(stub.id, ctx.exerciseResponse.id)
        assertEquals(stub.name, ctx.exerciseResponse.name)
        assertEquals(stub.description, ctx.exerciseResponse.description)
        assertEquals(stub.visibility, ctx.exerciseResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = FhContext(
            command = FhCommand.DELETE,
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
            command = FhCommand.DELETE,
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
            command = FhCommand.DELETE,
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