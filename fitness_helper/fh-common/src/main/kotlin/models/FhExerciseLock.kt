package models

import kotlin.jvm.JvmInline

@JvmInline
value class FhExerciseLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = FhExerciseLock("")
    }
}