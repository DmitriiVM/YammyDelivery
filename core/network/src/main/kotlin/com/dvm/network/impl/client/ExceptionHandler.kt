package com.dvm.network.impl.client

import android.content.Context
import com.dvm.network.impl.getNetworkCapabilities
import com.dvm.network.impl.isConnected
import com.dvm.network.impl.isWifiError
import com.dvm.utils.AppException
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.http.*

internal fun HttpClientConfig<*>.ExceptionHandler(
    context: Context
) = HttpResponseValidator {
    validateResponse { response ->
        when (response.status.value) {
            HttpStatusCode.NotModified.value -> throw AppException.NotModifiedException
            HttpStatusCode.BadRequest.value -> throw AppException.BadRequest
            HttpStatusCode.PaymentRequired.value -> throw AppException.IncorrectData
        }
    }
    handleResponseException {
        if (it !is AppException) {
            val networkCapabilities = context.getNetworkCapabilities()
            if (networkCapabilities?.isConnected() != true) {
                if (networkCapabilities.isWifiError()) {
                    throw AppException.WifiException
                } else {
                    throw AppException.CellularException
                }
            } else {
                throw AppException.GeneralException
            }
        }
    }
}