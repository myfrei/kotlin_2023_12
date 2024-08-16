package helpers

import models.FhError


fun Throwable.asFhError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = FhError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)