package com.example.composerecipeapp.core.network

sealed class Response{

    data class Success<T>(var response: T) : Response()
    data class Error<T>(var error: T ) : Response()
}



