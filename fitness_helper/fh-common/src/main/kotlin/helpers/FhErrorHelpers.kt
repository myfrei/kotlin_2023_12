package helpers

import FhContext
import models.FhError
import models.FhState


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

inline fun FhContext.addError(vararg error: FhError) = errors.addAll(error)
inline fun FhContext.addErrors(error: Collection<FhError>) = errors.addAll(error)

inline fun FhContext.fail(error: FhError) {
    addError(error)
    state = FhState.FAILING
}
inline fun FhContext.fail(errors: Collection<FhError>) {
    addErrors(errors)
    state = FhState.FAILING
}
inline fun errorValidation(
    field: String,
    violationCode: String,
    description: String
) = FhError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
)
inline fun errorSystem(
    violationCode: String,
    e: Throwable,
) = FhError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    exception = e,
)