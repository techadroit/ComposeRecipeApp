package com.example.composerecipeapp.ui

typealias Dispatch<T> = (T) -> Unit

typealias MultipleDispatch<T, E> = (T, E) -> Unit

typealias Navigate = (String) -> Unit

typealias PopBackStack = () -> Unit
