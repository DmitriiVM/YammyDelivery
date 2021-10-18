package com.dvm.utils

import com.dvm.ui.R as CoreR

sealed class AppException : Exception() {
    object WifiException : AppException()
    object CellularException : AppException()
    object GeneralException : AppException()
    object NotModifiedException : AppException()
    object BadRequest : AppException()
    object IncorrectData : AppException()
}

fun Throwable.getErrorMessage() =
    when (this) {
        is AppException.WifiException -> CoreR.string.message_network_error
        is AppException.CellularException -> CoreR.string.message_network_error
        else -> CoreR.string.message_general_error
    }