package com.feature.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

typealias LifeCycleObserverComposable = (Lifecycle.Event) -> Unit

@Composable
fun LifeCycleComposable(observer: LifeCycleObserverComposable) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            observer(event)
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}

@Composable
fun OnCreate(created: () -> Unit) {
    LifeCycleComposable {
        if (it.equals(Lifecycle.Event.ON_CREATE)) {
            created()
        }
    }
}

@Composable
fun OnDestroy(destroyed: () -> Unit) {
    LifeCycleComposable {
        if (it.equals(Lifecycle.Event.ON_DESTROY)) {
            destroyed()
        }
    }
}

@Composable
fun OnResumed(resumed: () -> Unit) {
    LifeCycleComposable {
        if (it.equals(Lifecycle.Event.ON_RESUME)) {
            resumed()
        }
    }
}

@Composable
fun OnPaused(paused: () -> Unit) {
    LifeCycleComposable {
        if (it.equals(Lifecycle.Event.ON_PAUSE)) {
            paused()
        }
    }
}