package com.dvm.utils

import android.content.Context

sealed class AppException : Exception() {
    object WifiException : AppException()
    object CellularException : AppException()
    object UnknownException : AppException()
    object NotModifiedException : AppException()
    object BadRequest : AppException()
    object IncorrectData : AppException()
}

fun Throwable.getErrorMessage(context: Context) =
    when (this) {
        is AppException.WifiException -> context.getString(R.string.message_network_error)
        is AppException.CellularException -> context.getString(R.string.message_network_error)
        else -> context.getString(R.string.message_unknown_error)
    }