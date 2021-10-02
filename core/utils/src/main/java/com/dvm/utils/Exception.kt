package com.dvm.utils

sealed class AppException : Exception() {
    data class ApiException(
        override val message: String,
        val code: Int
    ) : AppException()

    object TimeoutException : AppException()
    object NetworkException : AppException()
    object UnknownException : AppException()
}

fun Throwable.getErrorMessage() =
    when (this) {
        is AppException.TimeoutException -> R.string.message_network_timeout
        is AppException.NetworkException -> R.string.message_network_error
        else -> R.string.message_unknown_error
    }

fun Exception.hasCode(code: Int) =
    this is AppException.ApiException && this.code == code