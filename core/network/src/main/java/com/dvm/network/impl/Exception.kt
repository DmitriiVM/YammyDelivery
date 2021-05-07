package com.dvm.network.impl

sealed class AppException : Exception() {
    data class ApiException(
        override val message: String,
        val code: Int
    ) : AppException()

    object TimeoutException : AppException()
    object NetworkException : AppException()
    object UnknownException : AppException()
}