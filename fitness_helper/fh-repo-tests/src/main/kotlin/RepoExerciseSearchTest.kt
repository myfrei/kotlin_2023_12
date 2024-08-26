import models.*
import repo.DbExerciseFilterRequest
import repo.DbExercisesResponseOk
import repo.IRepoExercise
import kotlin.test.*

abstract class RepoExerciseSearchTest {
    abstract val repo: IRepoExercise

    protected open val initializedObjects: List<FhExercise> = initObjects

    fun searchOwner() = runRepoTest {
        val result = repo.searchExercise(DbExerciseFilterRequest(ownerId = searchOwnerId))
        assertIs<DbExercisesResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitExercises("search") {

        val searchOwnerId = FhUserId("owner-124")
        override val initObjects: List<FhExercise> = listOf(
            createInitTestModel("exercise1"),
            createInitTestModel("exercise2", ownerId = searchOwnerId),
            createInitTestModel("exercise3", ownerId = searchOwnerId),
        )
    }
}
