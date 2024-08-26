import models.*
import repo.DbExerciseIdRequest
import repo.DbExerciseResponseErr
import repo.DbExerciseResponseOk
import repo.IRepoExercise
import kotlin.test.*

abstract class RepoExerciseReadTest {
    abstract val repo: IRepoExercise
    protected open val readSucc = initObjects[0]

    fun readSuccess() = runRepoTest {
        val result = repo.readExercise(DbExerciseIdRequest(readSucc.id))

        assertIs<DbExerciseResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    fun readNotFound() = runRepoTest {
        val result = repo.readExercise(DbExerciseIdRequest(notFoundId))

        assertIs<DbExerciseResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitExercises("read") {
        override val initObjects: List<FhExercise> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = FhExerciseId("exercise-repo-read-notFound")
    }
}
