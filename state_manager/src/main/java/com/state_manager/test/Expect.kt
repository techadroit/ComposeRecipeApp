package com.state_manager.test

import com.state_manager.side_effects.SideEffect
import org.junit.Assert.assertEquals

fun TestResult.StateResult<*>.expect(states: List<Any>) {
    assertEquals(states, emittedStates)
}
fun <SIDE_EFFECT: SideEffect> TestResult.SideEffectsResult<SIDE_EFFECT>.expect(vararg effects: SIDE_EFFECT) {
    assertEquals(effects.toList(), emittedEffects)
}

fun TestResult.StateResult<*>.expectNotEmpty() {
    assert(emittedStates.isNotEmpty())
}