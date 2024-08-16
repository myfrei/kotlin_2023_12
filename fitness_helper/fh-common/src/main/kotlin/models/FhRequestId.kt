package models

import kotlin.jvm.JvmInline

@JvmInline
value class FhRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = FhRequestId("")
    }
}