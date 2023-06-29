package com.state_manager.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test

class CoroutineTestDemo {

    val word = "Hello World"
    suspend fun taskPrint(): String =
        withContext(Dispatchers.IO) {
            delay(100)
            return@withContext word
        }

    @Test
    fun testTaskPrint() {
        runTest {
            val response = taskPrint()
            assertEquals(word, response)
        }
    }
}