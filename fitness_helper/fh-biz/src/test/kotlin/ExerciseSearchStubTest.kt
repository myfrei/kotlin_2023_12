import kotlinx.coroutines.test.runTest
import com.fitness.helper.biz.FhExerciseProcessor
import com.fitness.helper.stubs.FhExerciseStub
import models.*
import stubs.FhStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ExerciseSearchStubTest {

    private val processor = FhExerciseProcessor()
    val filter = FhExerciseFilter(searchString = "push-up")

    @Test
    fun search() = runTest {

        val ctx = FhContext(
            command = FhCommand.SEARCH,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.SUCCESS,
            exerciseFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.exercisesResponse.size > 1)
        val first = ctx.exercisesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (FhExerciseStub.get()) {
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = FhContext(
            command = FhCommand.SEARCH,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_ID,
            exerciseFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = FhContext(
            command = FhCommand.SEARCH,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.DB_ERROR,
            exerciseFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = FhContext(
            command = FhCommand.SEARCH,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_NAME,
            exerciseFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}