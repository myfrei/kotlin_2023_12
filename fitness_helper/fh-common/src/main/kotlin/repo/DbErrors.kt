package repo

import helpers.errorSystem
import models.FhError
import models.FhExercise
import models.FhExerciseId
import models.FhExerciseLock
import repo.exceptions.RepoConcurrencyException
import repo.exceptions.RepoException


const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: FhExerciseId) = DbExerciseResponseErr(
    FhError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbExerciseResponseErr(
    FhError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldExercise: FhExercise,
    expectedLock: FhExerciseLock,
    exception: Exception = RepoConcurrencyException(
        id = oldExercise.id,
        expectedLock = expectedLock,
        actualLock = oldExercise.lock,
    ),
) = DbExerciseResponseErrWithData(
    exercise = oldExercise,
    err = FhError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldExercise.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: FhExerciseId) = DbExerciseResponseErr(
    FhError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Exercise ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbExerciseResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)