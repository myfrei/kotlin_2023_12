import models.FhExercise
import models.FhExerciseId
import repo.*
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoExerciseDeleteTest {
    abstract val repo: IRepoExercise
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = FhExerciseId("ad-repo-delete-notFound")

    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteExercise(DbExerciseIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals(deleteSucc.description, result.data.description)
    }

    fun deleteNotFound() = runRepoTest {
        val result = repo.readExercise(DbExerciseIdRequest(notFoundId))

        assertIs<DbExerciseResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteExercise(DbExerciseIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbExerciseResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitExercises("delete") {
        override val initObjects: List<FhExercise> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
