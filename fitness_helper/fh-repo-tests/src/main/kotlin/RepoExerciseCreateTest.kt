import models.*
import repo.DbExerciseRequest
import repo.DbExerciseResponseOk
import kotlin.test.*

abstract class RepoExerciseCreateTest {
    abstract val repo: IRepoExerciseInitializable
    protected open val uuidNew = FhExerciseId("10000000-0000-0000-0000-000000000001")

    private val createObj = FhExercise(
        name = "create object",
        description = "create object description",
        ownerId = FhUserId("owner-123"),
        visibility = FhVisibility.VISIBLE_TO_GROUP,
    )

    fun createSuccess() = runRepoTest {
        val result = repo.createExercise(DbExerciseRequest(createObj))
        val expected = createObj
        assertIs<DbExerciseResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.name, result.data.name)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(FhExerciseId.NONE, result.data.id)
    }

    companion object : BaseInitExercises("create") {
        override val initObjects: List<FhExercise> = emptyList()
    }
}