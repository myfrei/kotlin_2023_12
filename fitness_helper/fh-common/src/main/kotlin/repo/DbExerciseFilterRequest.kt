package repo

import models.FhUserId

data class DbExerciseFilterRequest(
    val nameFilter: String = "",
    val ownerId: FhUserId = FhUserId.NONE
)