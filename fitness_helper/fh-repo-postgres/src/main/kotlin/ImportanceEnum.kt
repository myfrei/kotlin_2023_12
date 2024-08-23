package ru.otus.otuskotlin.fh.repo.postgresql

import models.FhExerciseImportance
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

fun Table.importanceEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.IMPORTANCE_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.IMPORTANCE_LOW -> FhExerciseImportance.LOW
            SqlFields.IMPORTANCE_MEDIUM -> FhExerciseImportance.MEDIUM
            SqlFields.IMPORTANCE_HIGH -> FhExerciseImportance.HIGH
            else -> FhExerciseImportance.NONE
        }
    },
    toDb = { value ->
        when (value) {
            FhExerciseImportance.LOW -> PgImportanceLow
            FhExerciseImportance.MEDIUM -> PgImportanceMedium
            FhExerciseImportance.HIGH -> PgImportanceHigh
            FhExerciseImportance.NONE -> throw Exception("Wrong value of Importance. NONE is unsupported")
        }
    }
)

sealed class PgImportanceValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.IMPORTANCE_TYPE
        value = eValue
    }
}

object PgImportanceLow : PgImportanceValue(SqlFields.IMPORTANCE_LOW)
object PgImportanceMedium : PgImportanceValue(SqlFields.IMPORTANCE_MEDIUM)
object PgImportanceHigh : PgImportanceValue(SqlFields.IMPORTANCE_HIGH)