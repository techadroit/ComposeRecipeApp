package com.feature.common

import com.core.navigtion.intent.DestinationIntent

typealias Dispatch<T> = (T) -> Unit

typealias MultipleDispatch<T, E> = (T, E) -> Unit

typealias Navigate = (DestinationIntent) -> Unit

typealias PopBackStack = () -> Unit

typealias OnClick<T> = (T) -> Unit
