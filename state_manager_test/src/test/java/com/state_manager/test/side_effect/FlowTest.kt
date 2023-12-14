package com.state_manager.test.side_effect

import com.state_manager.test.BaseUnitTest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test


class FlowTest : BaseUnitTest() {

    val testManager = com.state_manager.test.TestStateManagerScope()

    val channel = Channel<Int>(capacity = Channel.UNLIMITED)

    @Test
    fun test1(){
        val flow1 = channel.receiveAsFlow()
        testManager.run {
            flow1.collect{
                println("value in 1 is $it")
            }
        }
        runTest{
            channel.send(1)
        }
    }
}