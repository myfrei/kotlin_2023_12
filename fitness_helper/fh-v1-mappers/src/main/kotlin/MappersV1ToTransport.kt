import com.fitness.helper.common.models.*
import exception.UnknownFhCommand
import models.*
import ru.white.api.models.*

fun FhContext.toTransportExercise(): IResponse = when (val cmd = command) {
    FhCommand.CREATE -> toTransportCreate()
    FhCommand.READ -> toTransportRead()
    FhCommand.UPDATE -> toTransportUpdate()
    FhCommand.DELETE -> toTransportDelete()
    FhCommand.SEARCH -> toTransportSearch()
    FhCommand.NONE -> throw UnknownFhCommand(cmd)
}

fun FhContext.toTransportCreate() = ExerciseCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exercise = exerciseResponse.toTransportExercise()
)

fun FhContext.toTransportRead() = ExerciseReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exercise = exerciseResponse.toTransportExercise()
)

fun FhContext.toTransportUpdate() = ExerciseUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exercise = exerciseResponse.toTransportExercise()
)

fun FhContext.toTransportDelete() = ExerciseDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exercise = exerciseResponse.toTransportExercise()
)

fun FhContext.toTransportSearch() = ExerciseSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exercises = exercisesResponse.toTransportExercise()
)

fun List<FhExercise>.toTransportExercise(): List<ExerciseResponseObject>? = this
    .map { it.toTransportExercise() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun FhExercise.toTransportExercise(): ExerciseResponseObject = ExerciseResponseObject(
    id = id.takeIf { it != FhExerciseId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != FhUserId.NONE }?.asString(),
    muscleGroup = muscleGroup.toTransportExercise(),
    permissions = permissionsClient.toTransportExercise(),
    lastPerformed = lastPerformed.toString(),
    nextReminder = nextReminder.toString(),
    addedOn = addedOn.toString(),
)

private fun Set<FhExercisePermissionClient>.toTransportExercise(): Set<ExercisePermissions>? {
    val result = this.map { it.toTransportExercise() }
        .toSet()
    return result.takeIf { it.isNotEmpty() } as Set<ExercisePermissions>?
}

internal fun FhExercisePermissionClient.toTransportExercise() = when (this) {
    FhExercisePermissionClient.READ -> ExercisePermissions.READ
    FhExercisePermissionClient.UPDATE -> ExercisePermissions.UPDATE
    FhExercisePermissionClient.REMIND -> FhExercisePermissionClient.REMIND
    FhExercisePermissionClient.DELETE -> ExercisePermissions.DELETE
}

internal fun FhMuscleGroup.toTransportExercise(): MuscleGroup? = when (this) {
    FhMuscleGroup.CHEST -> MuscleGroup.CHEST
    FhMuscleGroup.BACK -> MuscleGroup.BACK
    FhMuscleGroup.LEGS -> MuscleGroup.LEGS
    FhMuscleGroup.ARMS -> MuscleGroup.ARMS
    FhMuscleGroup.SHOULDERS -> MuscleGroup.SHOULDERS
    FhMuscleGroup.ABS -> MuscleGroup.ABS
    FhMuscleGroup.NONE -> null
}

internal fun List<FhError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportExercise() }
    .toList()
    .takeIf { it.isNotEmpty() }

internal fun FhError.toTransportExercise() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun FhState.toResult(): ResponseResult? = when (this) {
    FhState.RUNNING -> ResponseResult.SUCCESS
    FhState.FAILING -> ResponseResult.ERROR
    FhState.FINISHING -> ResponseResult.SUCCESS
    FhState.NONE -> null
}