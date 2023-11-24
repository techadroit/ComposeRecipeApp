package com.state_manager.side_effect

import com.state_manager.BaseUnitTest
import com.state_manager.logger.SystemOutLogger
import com.state_manager.side_effects.SideEffectHolderImpl
import com.state_manager.test.TestStateManagerScope
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SideEffectHolderTest : BaseUnitTest() {

    val testManagerScope = TestStateManagerScope()
    val holder =
        SideEffectHolderImpl<TestSideEffects>(SystemOutLogger(""), testManagerScope.getScope())

    @Test
    fun `test side effect emitted`() {
        runTest {
            val list = mutableListOf<TestSideEffects?>()
            backgroundScope.launch(testManagerScope.testDispatcher) {
                holder.effectObservable.collect {
                    list.add(it)
                }
            }
            holder.post(ShowToast)
            assertEquals(ShowToast, list.first())
            list.clear()
            holder.post(ShowDialog)
            assertEquals(ShowDialog, list.first())
            list.clear()
        }
    }
}