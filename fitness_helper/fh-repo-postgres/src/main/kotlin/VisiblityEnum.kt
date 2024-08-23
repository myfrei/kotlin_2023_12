package ru.otus.otuskotlin.fh.repo.postgresql

import models.FhVisibility
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

fun Table.visibilityEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.VISIBILITY_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.VISIBILITY_OWNER -> FhVisibility.VISIBLE_TO_OWNER
            SqlFields.VISIBILITY_GROUP -> FhVisibility.VISIBLE_TO_GROUP
            SqlFields.VISIBILITY_PUBLIC -> FhVisibility.VISIBLE_PUBLIC
            else -> FhVisibility.NONE
        }
    },
    toDb = { value ->
        when (value) {
            FhVisibility.VISIBLE_TO_OWNER -> PgVisibilityOwner
            FhVisibility.VISIBLE_TO_GROUP -> PgVisibilityGroup
            FhVisibility.VISIBLE_PUBLIC -> PgVisibilityPublic
            FhVisibility.NONE -> throw Exception("Wrong value of Visibility. NONE is unsupported")
        }
    }
)

sealed class PgVisibilityValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.VISIBILITY_TYPE
        value = eValue
    }
}

object PgVisibilityPublic : PgVisibilityValue(SqlFields.VISIBILITY_PUBLIC)
object PgVisibilityOwner : PgVisibilityValue(SqlFields.VISIBILITY_OWNER)
object PgVisibilityGroup : PgVisibilityValue(SqlFields.VISIBILITY_GROUP)