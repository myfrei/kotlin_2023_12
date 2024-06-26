# Функциональные требования
## Регистрация и управление учетной записью
### 1. Регистрация в приложении
**Источник требования**: Все типы пользователей

**Описание требования**: Реализовать функцию регистрации в приложении с обязательными полями:
- ФИО
- Электронная почта
- Пароль
- Дата рождения
- Пол
- Рост
- Вес
- Уровень физической активности

**Приоритет требования**: Высокий

### 2. Вход в систему
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Обеспечить аутентификацию пользователей по электронной почте и паролю
- Предусмотреть функцию восстановления пароля

**Приоритет требования**: Высокий

### 3. Управление профилем
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Разрешить редактирование профиля, включая личную информацию и данные
- Возможность обновлять вес и фотографию
- Отображение прогресса изменения веса

**Приоритет требования**: Средний

## Учет питания
### 1. Дневник питания
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Создать функционал для ручного ввода съеденных продуктов и блюд
- Интегрировать со сканером штрих-кодов для быстрого добавления продуктов
- Рассчитывать общую калорийность, белки, жиры и углеводы за день

**Приоритет требования**: Высокий

### 2. База данных продуктов
**Источник требования**: Разработчики, пользователи

**Описание требования**: Создать обширную базу данных продуктов с информацией о калориях и питательных веществах для упрощения ввода в дневник питания.

**Приоритет требования**: Высокий

### 3. Расчет потребностей
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Рассчитывать рекомендуемое дневное потребление калорий на основе данных пользователя (пол, возраст, рост, вес, активность)
- Выдавать рекомендации по потреблению белков, жиров и углеводов

**Приоритет требования**: Средний

## Планирование питания
### 1. Генератор меню
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Генерировать рекомендованные меню на день/неделю на основе данных и предпочтений пользователя
- Учитывать данные из дневника питания для исключения повторов блюд
- Добавлять/исключать отдельные блюда и продукты при генерации меню

**Приоритет требования**: Высокий

### 2. Ручное планирование
**Источник требования**: Зарегистрированные пользователи

**Описание требования**: Предоставить возможность создания собственных меню на день/неделю с расчетом КБЖУ

**Приоритет требования**: Средний

## Фитнес и здоровье
### 1. Калькулятор ИМТ
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Рассчитывать индекс массы тела на основе роста и веса пользователя
- Выдавать рекомендации по коррекции веса в зависимости от результата

**Приоритет требования**: Высокий

### 2. Библиотека упражнений
**Источник требования**: Разработчики, пользователи

**Описание требования**:
Создать базу данных упражнений с описаниями, изображениями/видео и информацией о задействованных группах мышц

**Приоритет требования**: Средний

### 3. Дневник тренировок
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Позволить пользователям вводить выполненные упражнения, количество подходов/повторений
- Рассчитывать приблизительное количество сожженных калорий
- Отслеживать прогресс силовых тренировок

**Приоритет требования**: Высокий

## Дополнительный функционал
### 1. Синхронизация с wearables
**Источник требования**: Зарегистрированные пользователи

**Описание требования**: Интегрировать приложение с фитнес-трекерами и смарт-часами для автоматического отслеживания активности и сна.

**Приоритет требования**: Низкий

### 2. Напоминания
**Источник требования**: Разработчики, пользователи

**Описание требования**: Реализовать систему напоминаний о приеме пищи, тренировках и водном балансе по расписанию.

**Приоритет требования**: Средний

### 3. Социальные функции
**Источник требования**: Зарегистрированные пользователи

**Описание требования**:
- Возможность публиковать результаты в ленте активности
- Делиться своим прогрессом
- Комментировать активность друзей

**Приоритет требования**: Низкий