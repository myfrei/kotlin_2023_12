import repo.IRepoExercise

class FhCorSettings(
    val loggerProvider:String = "nologs",
    val repoStub: IRepoExercise = IRepoExercise.NONE,
    val repoTest: IRepoExercise = IRepoExercise.NONE,
    val repoProd: IRepoExercise = IRepoExercise.NONE
) {
    companion object {
        val NONE = FhCorSettings()
    }
}