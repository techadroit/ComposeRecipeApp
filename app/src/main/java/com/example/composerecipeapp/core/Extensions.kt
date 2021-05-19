package com.example.composerecipeapp.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

/**
 * An extension property which can be used to make `when` expressions
 * exhaustive. Using `safe` makes it necessary to deal with all possible
 * scenarios when using the `when` statement.
 */
val Any.safe get() = Unit

fun <A, B, C> tripleOf(a: A, b: B, c: C) = Triple(a, b, c)

fun <A, B> pairOf(a: A, b: B) = Pair(a, b)

suspend fun <T> Flow<T>.last(): T {
    return this.reduce { _, value -> value }
}

fun <T> List<T>.append(other: List<T>?): List<T> {
    if (other.isNullOrEmpty()) return this
    return this + other
}

fun <T> List<T>?.hasAtLeastSize(minSize: Int): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.size >= minSize
}


inline fun <T> Flow<T>.collectIn(scope: CoroutineScope, crossinline action: suspend (value: T) -> Unit): Job =
    scope.launch {
        collect {
            action.invoke(it)
        }
    }
