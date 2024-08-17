package repo.exceptions

import models.FhExerciseId
import models.FhExerciseLock


class RepoConcurrencyException(id: FhExerciseId, expectedLock: FhExerciseLock, actualLock: FhExerciseLock?): RepoExerciseException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)