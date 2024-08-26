import models.FhExercise
import repo.IRepoExercise

interface IRepoExerciseInitializable: IRepoExercise {
    fun save(exercises: Collection<FhExercise>): Collection<FhExercise>
}