package com.test.searchacro.domain

import retrofit2.Response

data class ApiError(val statusCode: Int, val message: String)
fun <T : Any> Response<T>.toApiError(): ApiError {
    return ApiError(statusCode = this.code(), message = this.message())
}