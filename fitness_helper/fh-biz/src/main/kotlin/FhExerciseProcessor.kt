package com.fitness.helper.biz

import FhContext
import FhCorSettings
import com.fitness.helper.biz.general.initStatus
import com.fitness.helper.biz.general.operation
import com.fitness.helper.biz.general.stubs
import com.fitness.helper.biz.stubs.*
import com.fitness.helper.biz.validation.*
import models.FhCommand
import models.FhExerciseId
import models.FhExerciseLock
import rootChain
import worker

@Suppress("unused", "RedundantSuspendModifier")
class FhExerciseProcessor(private val corSettings: FhCorSettings = FhCorSettings.NONE) {
    suspend fun exec(ctx: FhContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<FhContext> {
        initStatus("Инициализация статуса")

        operation("Создание упражнения", FhCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешного создания упражнения", corSettings)
                stubValidationBadName("Имитация ошибки валидации имени")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в exerciseValidating") { exerciseValidating = exerciseRequest.deepCopy() }
                worker("Очистка id") { exerciseValidating.id = FhExerciseId.NONE }
                worker("Очистка имени") { exerciseValidating.name = exerciseValidating.name.trim() }
                worker("Очистка описания") { exerciseValidating.description = exerciseValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что название упражнения не пустое")
                validateTitleHasContent("Проверка, что название упражнения содержит контент")
                validateDescriptionNotEmpty("Проверка, что описание упражнения не пустое")
                validateDescriptionHasContent("Проверка, что описание упражнения содержит контент")

                finishExerciseValidation("Завершение валидации упражнения")
            }
        }

        operation("Получение упражнения", FhCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешного получения упражнения", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в exerciseValidating") { exerciseValidating = exerciseRequest.deepCopy() }
                worker("Очистка id") { exerciseValidating.id = FhExerciseId(exerciseValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                finishExerciseValidation("Успешное завершение процедуры валидации")
            }
        }

        operation("Удаление упражнения", FhCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешного удаления упражнения", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в exerciseValidating") {
                    exerciseValidating = exerciseRequest.deepCopy()
                }
                worker("Очистка id") { exerciseValidating.id = FhExerciseId(exerciseValidating.id.asString().trim()) }
                worker("Очистка lock") { exerciseValidating.lock = FhExerciseLock(exerciseValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                finishExerciseValidation("Успешное завершение процедуры валидации")
            }
        }

        operation("Обновление упражнения", FhCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешного обновления упражнения", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadName("Имитация ошибки валидации имени")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в exerciseValidating") { exerciseValidating = exerciseRequest.deepCopy() }
                worker("Очистка id") { exerciseValidating.id = FhExerciseId(exerciseValidating.id.asString().trim()) }
                worker("Очистка lock") { exerciseValidating.lock = FhExerciseLock(exerciseValidating.lock.asString().trim()) }
                worker("Очистка имени") { exerciseValidating.name = exerciseValidating.name.trim() }
                worker("Очистка описания") { exerciseValidating.description = exerciseValidating.description.trim() }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                validateTitleNotEmpty("Проверка на непустое название")
                validateTitleHasContent("Проверка содержания в названии")
                validateDescriptionNotEmpty("Проверка на непустое описание")
                validateDescriptionHasContent("Проверка содержания в описании")

                finishExerciseValidation("Успешное завершение процедуры валидации")
            }
        }

        operation("Поиск упражнений", FhCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешного поиска упражнений", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в exerciseFilterValidating") { exerciseFilterValidating = exerciseFilterRequest.deepCopy() }
                validateSearchStringLength("Валидация длины строки поиска в фильтре")

                finishExerciseFilterValidation("Успешное завершение процедуры валидации фильтра")
            }
        }
    }.build()
}