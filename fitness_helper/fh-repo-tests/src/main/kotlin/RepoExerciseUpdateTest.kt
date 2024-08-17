import models.*
import repo.*
import kotlin.test.*

abstract class RepoExerciseUpdateTest {
    abstract val repo: IRepoExercise
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = FhExerciseId("exercise-repo-update-not-found")
    protected val lockBad = FhExerciseLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = FhExerciseLock("20000000-0000-0000-0000-000000000002")
    protected val lockIdNotFound = FhExerciseLock("20000000-0000-0000-0000-000000000004")


    private val reqUpdateSucc by lazy {
        FhExercise(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            ownerId = FhUserId("owner-123"),
            visibility = FhVisibility.VISIBLE_TO_GROUP,
            lock = updateSucc.lock,
        )
    }
    private val reqUpdateNotFound = FhExercise(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        ownerId = FhUserId("owner-123"),
        visibility = FhVisibility.VISIBLE_TO_GROUP,
        lock = lockIdNotFound
    )

    private val reqUpdateConc by lazy {
        FhExercise(
            id = updateConc.id,
            name = "update object concurrency",
            description = "update object concurrency description",
            ownerId = FhUserId("owner-123"),
            visibility = FhVisibility.VISIBLE_TO_GROUP,
            lock = lockBad,
        )
    }


    fun updateSuccess() = runRepoTest {
        val result = repo.updateExercise(DbExerciseRequest(reqUpdateSucc))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.name, result.data.name)
        assertEquals(reqUpdateSucc.description, result.data.description)
        assertEquals(lockNew, result.data.lock)
    }


    fun updateNotFound() = runRepoTest {
        val result = repo.updateExercise(DbExerciseRequest(reqUpdateNotFound))
        assertIs<DbExerciseResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }


    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateExercise(DbExerciseRequest(reqUpdateConc))
        assertIs<DbExerciseResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
    }

    companion object : BaseInitExercises("update") {
        override val initObjects: List<FhExercise> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
    }