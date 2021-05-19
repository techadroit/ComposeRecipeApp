package com.example.composerecipeapp.core.usecase

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).async {
            val job = run(params)
            CoroutineScope(Dispatchers.Main).launch {
                onResult(job)
            }
        }
    }

}

abstract class FlowUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params):  Type

    operator fun invoke(params: Params) : Flow<Type> {
        return flow{
            val result = run(params)
            emit(result)
        }
    }

}

object None
