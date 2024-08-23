package ru.otus.otuskotlin.fh.repo.postgresql

import com.fitness.helper.common.models.FhMuscleGroup
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

fun Table.muscleGroupEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.MUSCLE_GROUP_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.MUSCLE_GROUP_CHEST -> FhMuscleGroup.CHEST
            SqlFields.MUSCLE_GROUP_BACK -> FhMuscleGroup.BACK
            SqlFields.MUSCLE_GROUP_LEGS -> FhMuscleGroup.LEGS
            SqlFields.MUSCLE_GROUP_ARMS -> FhMuscleGroup.ARMS
            SqlFields.MUSCLE_GROUP_SHOULDERS -> FhMuscleGroup.SHOULDERS
            SqlFields.MUSCLE_GROUP_ABS -> FhMuscleGroup.ABS
            else -> FhMuscleGroup.NONE
        }
    },
    toDb = { value ->
        when (value) {
            FhMuscleGroup.CHEST -> PgMuscleGroupChest
            FhMuscleGroup.BACK -> PgMuscleGroupBack
            FhMuscleGroup.LEGS -> PgMuscleGroupLegs
            FhMuscleGroup.ARMS -> PgMuscleGroupArms
            FhMuscleGroup.SHOULDERS -> PgMuscleGroupShoulders
            FhMuscleGroup.ABS -> PgMuscleGroupAbs
            FhMuscleGroup.NONE -> throw Exception("Wrong value of Muscle Group. NONE is unsupported")
        }
    }
)

sealed class PgMuscleGroupValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.MUSCLE_GROUP_TYPE
        value = eValue
    }
}

object PgMuscleGroupChest : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_CHEST)
object PgMuscleGroupBack : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_BACK)
object PgMuscleGroupLegs : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_LEGS)
object PgMuscleGroupArms : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_ARMS)
object PgMuscleGroupShoulders : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_SHOULDERS)
object PgMuscleGroupAbs : PgMuscleGroupValue(SqlFields.MUSCLE_GROUP_ABS)