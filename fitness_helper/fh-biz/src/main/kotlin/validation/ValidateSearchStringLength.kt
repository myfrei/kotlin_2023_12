package com.fitness.helper.biz.validation

import FhContext
import ICorChainDsl
import chain
import helpers.errorValidation
import helpers.fail
import models.FhState
import worker


fun ICorChainDsl<FhContext>.validateSearchStringLength(title: String) = chain {
    this.title = title
    this.description = """
        Валидация длины строки поиска в фильтрах упражнений. Допустимые значения:
        - null - не выполняем поиск по строке
        - 3-100 - допустимая длина
        - больше 100 - слишком длинная строка
    """.trimIndent()
    on { state == FhState.RUNNING }
    worker("Обрезка пустых символов") { exerciseFilterValidating.searchString = exerciseFilterValidating.searchString.trim() }
    worker {
        this.title = "Проверка кейса длины на 0-2 символа"
        this.description = this.title
        on { state == FhState.RUNNING && exerciseFilterValidating.searchString.length in (1..2) }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooShort",
                    description = "Search string must contain at least 3 symbols"
                )
            )
        }
    }
    worker {
        this.title = "Проверка кейса длины на более 100 символов"
        this.description = this.title
        on { state == FhState.RUNNING && exerciseFilterValidating.searchString.length > 100 }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooLong",
                    description = "Search string must be no more than 100 symbols long"
                )
            )
        }
    }
}