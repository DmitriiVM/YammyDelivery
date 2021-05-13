package com.dvm.utils

import android.content.Context

sealed class AppException : Exception() {
    data class ApiException(
        override val message: String,
        val code: Int
    ) : AppException()

    object TimeoutException : AppException()
    object NetworkException : AppException()
    object UnknownException : AppException()
}

fun Exception.getErrorMessage(context: Context) =
    when (this) {
        is AppException.ApiException -> this.message
        is AppException.TimeoutException -> context.getString(R.string.message_network_timeout)
        is AppException.NetworkException -> context.getString(R.string.message_network_error)
        else -> context.getString(R.string.message_unknown_error)
    }

fun Exception.hasCode(code: Int) =
    this is AppException.ApiException && this.code == code