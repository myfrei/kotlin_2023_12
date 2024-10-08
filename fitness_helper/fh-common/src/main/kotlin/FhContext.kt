import kotlinx.datetime.Instant
import models.*
import repo.IRepoExercise
import stubs.FhStubs

data class FhContext(
    var command: FhCommand = FhCommand.NONE,
    var state: FhState = FhState.NONE,
    val errors: MutableList<FhError> = mutableListOf(),

    var workMode: FhWorkMode = FhWorkMode.PROD,
    var stubCase: FhStubs = FhStubs.NONE,

    var requestId: FhRequestId = FhRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var exerciseRequest: FhExercise = FhExercise(),
    var exerciseFilterRequest: FhExerciseFilter = FhExerciseFilter(),

    var exerciseValidating: FhExercise = FhExercise(),
    var exerciseFilterValidating: FhExerciseFilter = FhExerciseFilter(),

    var exerciseValidated: FhExercise = FhExercise(),
    var exerciseFilterValidated: FhExerciseFilter = FhExerciseFilter(),
    var exerciseResponse: FhExercise = FhExercise(),
    var exercisesResponse: MutableList<FhExercise> = mutableListOf(),

    var corSettings: FhCorSettings = FhCorSettings(),

    var exerciseRepo: IRepoExercise = IRepoExercise.NONE,
    var exerciseRepoRead: FhExercise = FhExercise(),
    var exerciseRepoPrepare: FhExercise = FhExercise(),
    var exerciseRepoDone: FhExercise = FhExercise(),
    var exercisesRepoDone: MutableList<FhExercise> = mutableListOf(),
)