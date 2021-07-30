package com.example.composerecipeapp.core.functional

/**
 * A sealed class to represent UI states associated with a resource.
 */
sealed class Resource<out T> {

    abstract override fun hashCode(): Int
    abstract override fun equals(other: Any?): Boolean

    /**
     * A data class to represent the scenario where the resource is available without any errors
     */
    data class Success <out T>(val data: T, val isCached: Boolean = false) : Resource<T>() {
        operator fun invoke(): T {
            return data
        }
    }

    data class ListData<T>(val data: List<T>, val isCached: Boolean = false) : Resource<T>() {

        var cachedData = mutableListOf<T>()

        init {
            if (isCached) {
                cachedData.addAll(data)
            }
        }

        operator fun invoke(): List<T> {
            return data
        }
    }

    /**
     * A data class to represent the scenario where a resource may or may not be available due to an error
     */
    data class Error <out E>(val error: E?) : Resource<E>()

    /**
     * A class to represent the loading state of an object
     */
    object Loading : Resource<Nothing>() {
        override fun hashCode(): Int {
            return 2
        }

        override fun equals(other: Any?): Boolean {
            return other is Loading
        }
    }

    object Uninitialized : Resource<Nothing>() {
        override fun hashCode(): Int {
            return 1
        }

        override fun equals(other: Any?): Boolean {
            return other is Uninitialized
        }
    }
}

operator fun <T> Resource<T>.invoke(): T? {
    return when {
        this is Resource.Success -> this.data
        this is Resource.Error<T> && this.error != null -> this.error
        else -> null
    }
}
