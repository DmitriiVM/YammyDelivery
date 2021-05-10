package com.dvm.network.impl

import com.dvm.utils.hasCode

internal suspend fun <T> getModified(call: suspend () -> T): T? =
    try {
        call()
    } catch (exception: Exception) {
        when {
            exception.hasCode(304) -> null
            else -> throw exception
        }
    }