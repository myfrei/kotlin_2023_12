import models.FhExercise


/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class ExerciseRepoInitialized(
    val repo: IRepoExerciseInitializable,
    initObjects: Collection<FhExercise> = emptyList(),
) : IRepoExerciseInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<FhExercise> = save(initObjects).toList()
}
