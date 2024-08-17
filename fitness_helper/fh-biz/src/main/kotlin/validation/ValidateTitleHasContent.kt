package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import helpers.errorValidation
import helpers.fail
import worker

fun ICorChainDsl<FhContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что у названия упражнения есть какие-то слова.
        Отказываем в сохранении названий, содержащих только бессмысленные символы.
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { exerciseValidating.name.isNotEmpty() && !exerciseValidating.name.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}