package com.dvm.network.client

import android.content.Context
import android.util.Log
import com.dvm.network.util.getNetworkCapabilities
import com.dvm.network.util.isConnected
import com.dvm.network.util.isWifiError
import com.dvm.utils.AppException
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.*
import io.ktor.http.HttpStatusCode

internal fun HttpClientConfig<*>.ExceptionHandler(
    context: Context
) = HttpResponseValidator {
    validateResponse { response ->
        Log.d("mmm", " :  ExceptionHandler -response-  $response")
        when (response.status.value) {
            HttpStatusCode.NotModified.value -> throw AppException.NotModifiedException
            HttpStatusCode.BadRequest.value -> throw AppException.BadRequest
            HttpStatusCode.PaymentRequired.value -> throw AppException.IncorrectData
        }
    }
    handleResponseExceptionWithRequest { cause, request ->
        if (cause !is AppException) {
            val networkCapabilities = context.getNetworkCapabilities()
            if (networkCapabilities?.isConnected() != true) {
                if (networkCapabilities.isWifiError()) {
                    throw AppException.WifiException
                } else {
                    throw AppException.CellularException
                }
            } else {
                Log.d("mmm", " :  ExceptionHandler -cause-  $cause")
                throw AppException.GeneralException
            }
        }
    }
}