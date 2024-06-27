package models

import kotlin.jvm.JvmInline

@JvmInline
value class FhExerciseId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = FhExerciseId("")
    }
}