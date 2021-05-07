package com.dvm.utils.extensions

import com.dvm.network.impl.AppException
import com.dvm.utils.R
import com.dvm.utils.StringProvider

fun Exception.getErrorMessage(stringProvider: StringProvider) =
    when (this) {
        is AppException.ApiException -> this.message
        is AppException.TimeoutException -> stringProvider.getString(R.string.message_network_timeout)
        is AppException.NetworkException -> stringProvider.getString(R.string.message_network_error)
        else -> stringProvider.getString(R.string.message_unknown_error)
    }

fun Exception.hasCode(code: Int) =
    this is AppException.ApiException && this.code == code