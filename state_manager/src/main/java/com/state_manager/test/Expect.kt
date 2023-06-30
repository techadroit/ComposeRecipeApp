package com.state_manager.test

import org.junit.Assert.assertEquals

fun TestResult.StateResult<*>.expect(states: List<Any>) {
    assertEquals(states, emittedStates)
}

fun TestResult.StateResult<*>.expectNotEmpty() {
    assert(emittedStates.isNotEmpty())
}