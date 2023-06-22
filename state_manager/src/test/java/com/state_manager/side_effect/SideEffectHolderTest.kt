package com.state_manager.side_effect

import app.cash.turbine.test
import com.state_manager.BaseUnitTest
import com.state_manager.logger.SystemOutLogger
import com.state_manager.side_effects.SideEffectHolderImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SideEffectHolderTest : BaseUnitTest() {

    val holder = SideEffectHolderImpl<TestSideEffects>(SystemOutLogger(""))

    @OptIn(InternalCoroutinesApi::class)
    @Test
    fun `test side effect emitted`(){
        runTest {
            holder.post(ShowToast)
            holder.effectObservable.test {
                assertEquals(ShowToast,awaitItem()?.consume())
            }
        }
    }
}