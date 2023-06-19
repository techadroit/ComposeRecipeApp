package com.feature.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.state_manager.managers.StateEventManager
import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import java.util.*

fun Modifier.fullScreen() = this
    .fillMaxWidth()
    .fillMaxHeight()

fun toViews(value: Long): String {
    val suffixes = TreeMap<Long, String>()
    suffixes[1_000L] = "k"
    suffixes[1_000_000L] = "M"
    suffixes[1_000_0000L] = "Cr"
    suffixes[1_000_000_000L] = "G"
    suffixes[1_000_000_000_000L] = "T"
    suffixes[1_000_000_000_000_000L] = "P"
    suffixes[1_000_000_000_000_000_000L] = "E"
    // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
    if (value == Long.MIN_VALUE) return toViews(Long.MIN_VALUE + 1)
    if (value < 0) return "-" + toViews(-value)
    if (value < 1000) return java.lang.Long.toString(value) // deal with easy case
    val e: Map.Entry<Long, String> = suffixes.floorEntry(value)
    val divideBy = e.key
    val suffix = e.value
    val truncated = value / (divideBy / 10) // the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}

@Composable
fun <S : AppState, E : AppEvent> StateEventManager<S, E>.observeSideEffect( content:@Composable (SideEffect)->Unit) {
    onSideEffect().collectAsState().value?.let {
        it.consume()?.let{content(it)}
    }
}

@Composable
fun <S : AppState, E : AppEvent> Manager<S, E>.observeState() =
    this.stateEmitter.collectAsState().value
