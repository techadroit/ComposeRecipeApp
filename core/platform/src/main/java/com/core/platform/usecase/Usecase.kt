package com.core.platform.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> {

    abstract suspend fun run(params: Params): Type
}

// abstract class FlowUseCase<out Type, in Params> where Type : Any {
//
//    abstract suspend fun run(params: Params): Type
//
//    operator fun invoke(params: Params): Flow<Type> {
//        return flow {
//            val result = run(params)
//            emit(result)
//        }
//    }
//
// }

abstract class FlowUseCase<out Type, in Params> : UseCase<Type, Params>() {
    operator fun invoke(params: Params): Flow<Type> {
        return flow {
            val result = run(params)
            emit(result)
        }
    }
}

abstract class NewFlowUseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params): Flow<Type> = run(params).flowOn(Dispatchers.IO)
}

object None
