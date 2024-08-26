package ru.otus.otuskotlin.fh.repo.postgresql

object SqlFields {
    const val ID = "id"
    const val NAME = "name"
    const val DESCRIPTION = "description"
    const val VISIBILITY = "visibility"
    const val LOCK = "lock"
    const val OWNER_ID = "owner_id"

    const val IMPORTANCE = "importance"
    const val MUSCLE_GROUP = "muscle_group"

    const val LAST_PERFORMED = "last_performed"
    const val NEXT_REMINDER = "next_reminder"
    const val ADDED_ON = "added_on"
    const val APPEND = "append"

    const val VISIBILITY_TYPE = "exercise_visibility_type"
    const val VISIBILITY_PUBLIC = "public"
    const val VISIBILITY_OWNER = "owner"
    const val VISIBILITY_GROUP = "group"

    const val IMPORTANCE_TYPE = "exercise_importance_type"
    const val IMPORTANCE_LOW = "low"
    const val IMPORTANCE_MEDIUM = "medium"
    const val IMPORTANCE_HIGH = "high"

    const val MUSCLE_GROUP_TYPE = "muscle_group_type"
    const val MUSCLE_GROUP_CHEST = "chest"
    const val MUSCLE_GROUP_BACK = "back"
    const val MUSCLE_GROUP_LEGS = "legs"
    const val MUSCLE_GROUP_ARMS = "arms"
    const val MUSCLE_GROUP_SHOULDERS = "shoulders"
    const val MUSCLE_GROUP_ABS = "abs"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, NAME, DESCRIPTION, VISIBILITY, LOCK, OWNER_ID, IMPORTANCE, MUSCLE_GROUP, LAST_PERFORMED, NEXT_REMINDER, ADDED_ON, APPEND
    )
}