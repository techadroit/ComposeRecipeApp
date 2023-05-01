package com.example.composerecipeapp.ui.util

import com.core.navigtion.DestinationIntent

typealias Dispatch<T> = (T) -> Unit

typealias MultipleDispatch<T, E> = (T, E) -> Unit

typealias Navigate = (com.core.navigtion.DestinationIntent) -> Unit

typealias PopBackStack = () -> Unit

typealias OnClick<T> = (T) -> Unit
