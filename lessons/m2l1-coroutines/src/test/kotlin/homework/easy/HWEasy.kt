package ru.otus.otuskotlin.coroutines.homework.easy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class HWEasy {

    @Test
    fun easyHw() = runBlocking {
        val numbers = generateNumbers()
        val toFind = 10
        val toFindOther = 1000

        val res1 = async(Dispatchers.Default) {
            findNumberInList(toFind, numbers)
        }

        val res2 = async(Dispatchers.Default) {
            findNumberInList(toFindOther, numbers)
        }

        val foundNumbers = listOf(
            res1.await(),
            res2.await()
            //findNumberInList(toFind, numbers),
            //findNumberInList(toFindOther, numbers)
        )

        foundNumbers.forEach {
            if (it != -1) {
                println("Your number $it found!")
            } else {
                println("Not found number $toFind || $toFindOther")
            }
        }
    }
}
