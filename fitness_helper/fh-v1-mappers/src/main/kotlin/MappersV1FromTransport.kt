import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents
import com.fitness.helper.common.models.*
import exceptions.UnknownRequestClass
import models.*
import ru.white.api.models.*
import stubs.FhStubs

fun FhContext.fromTransport(request: IRequest) = when (request) {
    is ExerciseCreateRequest -> fromTransport(request)
    is ExerciseReadRequest -> fromTransport(request)
    is ExerciseUpdateRequest -> fromTransport(request)
    is ExerciseDeleteRequest -> fromTransport(request)
    is ExerciseSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toExerciseId() = this?.let { FhExerciseId(it) } ?: FhExerciseId.NONE
private fun String?.toExerciseWithId() = FhExercise(id = this.toExerciseId())
private fun String?.toExerciseLock() = this?.let { FhExerciseLock(it) } ?: FhExerciseLock.NONE

private fun ExerciseDebug?.transportToWorkMode(): FhWorkMode = when (this?.mode) {
    ExerciseRequestDebugMode.PROD -> FhWorkMode.PROD
    ExerciseRequestDebugMode.TEST -> FhWorkMode.TEST
    ExerciseRequestDebugMode.STUB -> FhWorkMode.STUB
    null -> FhWorkMode.PROD
}

private fun ExerciseDebug?.transportToStubCase(): FhStubs = when (this?.stub) {
    ExerciseRequestDebugStubs.SUCCESS -> FhStubs.SUCCESS
    ExerciseRequestDebugStubs.NOT_FOUND -> FhStubs.NOT_FOUND
    ExerciseRequestDebugStubs.BAD_ID -> FhStubs.BAD_ID
    ExerciseRequestDebugStubs.BAD_NAME -> FhStubs.BAD_NAME
    ExerciseRequestDebugStubs.BAD_DESCRIPTION -> FhStubs.BAD_DESCRIPTION
    ExerciseRequestDebugStubs.CANNOT_DELETE -> FhStubs.CANNOT_DELETE
    ExerciseRequestDebugStubs.BAD_SEARCH_STRING -> FhStubs.BAD_SEARCH_STRING
    null -> FhStubs.NONE
}

fun FhContext.fromTransport(request: ExerciseCreateRequest) {
    command = FhCommand.CREATE
    exerciseRequest = request.exercise?.toInternal() ?: FhExercise()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FhContext.fromTransport(request: ExerciseReadRequest) {
    command = FhCommand.READ
    exerciseRequest = request.exercise.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExerciseReadObject?.toInternal(): FhExercise = if (this != null) {
    FhExercise(id = id.toExerciseId())
} else {
    FhExercise()
}

fun FhContext.fromTransport(request: ExerciseUpdateRequest) {
    command = FhCommand.UPDATE
    exerciseRequest = request.exercise?.toInternal() ?: FhExercise()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun FhContext.fromTransport(request: ExerciseDeleteRequest) {
    command = FhCommand.DELETE
    exerciseRequest = request.exercise.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExerciseDeleteObject?.toInternal(): FhExercise = if (this != null) {
    FhExercise(
        id = id.toExerciseId(),
        lock = lock.toExerciseLock(),
    )
} else {
    FhExercise()
}

fun FhContext.fromTransport(request: ExerciseSearchRequest) {
    command = FhCommand.SEARCH
    exerciseFilterRequest = request.exerciseFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExerciseSearchFilter?.toInternal(): FhExerciseFilter = FhExerciseFilter(
    searchString = this?.searchString ?: ""
)

private fun getInstant(timeString: String): Instant {
    val customFormat = DateTimeComponents.Format {
        dateTime(LocalDateTime.Formats.ISO)
        offset(UtcOffset.Formats.ISO)
    }
    val ldt: LocalDateTime = customFormat.parse(timeString).toLocalDateTime()
    return ldt.toInstant(TimeZone.UTC)
}

private fun ExerciseCreateObject.toInternal(): FhExercise = FhExercise(
    name = this.name ?: "",
    description = this.description ?: "",
    muscleGroup = this.muscleGroup.fromTransport(),
    lastPerformed = this.lastPerformed?.let { getInstant(it) } ?: Instant.NONE,
    nextReminder = this.nextReminder?.let { getInstant(it) } ?: Instant.NONE,
    addedOn = this.addedOn?.let { getInstant(it) } ?: Instant.NONE,
)

private fun ExerciseUpdateObject.toInternal(): FhExercise = FhExercise(
    id = this.id.toExerciseId(),
    name = this.name ?: "",
    description = this.description ?: "",
    muscleGroup = this.muscleGroup.fromTransport(),
    lastPerformed = this.lastPerformed?.let { getInstant(it) } ?: Instant.NONE,
    nextReminder = this.nextReminder?.let { getInstant(it) } ?: Instant.NONE,
    addedOn = this.addedOn?.let { getInstant(it) } ?: Instant.NONE,
    lock = lock.toExerciseLock(),
)

private fun MuscleGroup?.fromTransport(): FhMuscleGroup = when (this) {
    MuscleGroup.CHEST -> FhMuscleGroup.CHEST
    MuscleGroup.BACK -> FhMuscleGroup.BACK
    MuscleGroup.LEGS -> FhMuscleGroup.LEGS
    MuscleGroup.ARMS -> FhMuscleGroup.ARMS
    MuscleGroup.SHOULDERS -> FhMuscleGroup.SHOULDERS
    MuscleGroup.ABS -> FhMuscleGroup.ABS
    null -> FhMuscleGroup.NONE
}