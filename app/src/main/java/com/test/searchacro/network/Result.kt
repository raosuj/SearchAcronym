package com.test.searchacro.network

import com.test.searchacro.domain.ApiError


sealed class Result<out T : Any> {
    /**
     * Successful result of request without errors
     */
    class Success<out T : Any>(val value: T) : Result<T>() {
        override fun toString() = "Result.Ok{value=$value}"
    }

    /**
     * HTTP error
     */
    class Error(val apiError: ApiError) : Result<Nothing>(){
        override fun toString() = "Result.Error{exception=$apiError}"
    }

    class Exception(val exception: Throwable): Result<Nothing>() {
        override fun toString() = "Result.Exception{$exception}"
    }
}

/**
 * Returns [Result.Success.value] or `null`
 */
fun <T : Any> Result<T>.getOrNull(): T? = (this as? Result.Success)?.value