package com.dvm.utils

sealed class AppException : Exception() {
    object WifiException : AppException()
    object CellularException : AppException()
    object UnknownException : AppException()
    object NotModifiedException : AppException()
    object BadRequest : AppException()
    object IncorrectData : AppException()
}

fun Throwable.getErrorMessage() =
    when (this) {
        is AppException.WifiException -> R.string.message_network_error
        is AppException.CellularException -> R.string.message_network_error
        else -> R.string.message_unknown_error
    }