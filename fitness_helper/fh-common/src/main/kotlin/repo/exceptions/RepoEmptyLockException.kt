package repo.exceptions

import models.FhExerciseId


class RepoEmptyLockException(id: FhExerciseId): RepoExerciseException(
    id,
    "Lock is empty in DB"
)