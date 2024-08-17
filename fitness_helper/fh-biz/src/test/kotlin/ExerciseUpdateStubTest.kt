import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import com.fitness.helper.biz.FhExerciseProcessor
import models.*
import stubs.FhStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseUpdateStubTest {

    private val processor = FhExerciseProcessor()
    val id = FhExerciseId("test-exercise-id")
    val name = "test name"
    val description = "test description"
    val visibility = FhVisibility.VISIBLE_PUBLIC
    var importance = FhExerciseImportance.LOW
    var append = Instant.NONE

    @Test
    fun update() = runTest {

        val ctx = FhContext(
            command = FhCommand.UPDATE,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.SUCCESS,
            exerciseRequest = FhExercise(
                id = id,
                name = name,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.exerciseResponse.id)
        assertEquals(name, ctx.exerciseResponse.name)
        assertEquals(description, ctx.exerciseResponse.description)
        assertEquals(visibility, ctx.exerciseResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = FhContext(
            command = FhCommand.UPDATE,
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
    fun badName() = runTest {
        val ctx = FhContext(
            command = FhCommand.UPDATE,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_NAME,
            exerciseRequest = FhExercise(
                id = id,
                name = "",
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = FhContext(
            command = FhCommand.UPDATE,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_DESCRIPTION,
            exerciseRequest = FhExercise(
                id = id,
                name = name,
                description = "",
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = FhContext(
            command = FhCommand.UPDATE,
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
            command = FhCommand.UPDATE,
            state = FhState.NONE,
            workMode = FhWorkMode.STUB,
            stubCase = FhStubs.BAD_SEARCH_STRING,
            exerciseRequest = FhExercise(
                id = id,
                name = name,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(FhExercise(), ctx.exerciseResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}