package com.example.composerecipeapp.ui

import com.example.composerecipeapp.platform.navigation.navigator.DestinationIntent

typealias Dispatch<T> = (T) -> Unit

typealias MultipleDispatch<T, E> = (T, E) -> Unit

typealias Navigate = (DestinationIntent) -> Unit

typealias PopBackStack = () -> Unit

typealias OnClick<T> = (T) -> Unit
