package com.dvm.network.impl

import android.content.Context
import com.dvm.utils.AppException
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ExceptionCallAdapterFactory(
    private val context: Context
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<<Foo> or Call<out Foo>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        return ResponseCallAdapter<Any>(context, responseType)
    }

    private class ResponseCallAdapter<T>(
        private val context: Context,
        private val responseType: Type
    ) : CallAdapter<T, Call<T>> {

        override fun responseType(): Type = responseType

        override fun adapt(call: Call<T>): Call<T> = ResponseCall(context, call)
    }

    private class ResponseCall<T>(
        private val context: Context,
        private val delegate: Call<T>,
    ) : Call<T> {
        override fun enqueue(callback: Callback<T>) {
            delegate.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        callback.onResponse(this@ResponseCall, response)
                    } else {
                        val exception = when (response.code()) {
                            304 -> AppException.NotModifiedException
                            400 -> AppException.BadRequest
                            402 -> AppException.IncorrectData
                            else -> AppException.GeneralException
                        }
                        callback.onFailure(this@ResponseCall, exception)
                    }
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    val networkCapabilities = context.getNetworkCapabilities()
                    val exception = if (networkCapabilities?.isConnected() != true) {
                        if (networkCapabilities.isWifiError()) {
                            AppException.WifiException
                        } else {
                            AppException.CellularException
                        }
                    } else {
                        AppException.GeneralException
                    }
                    callback.onFailure(this@ResponseCall, exception)
                }
            })
        }

        override fun clone(): Call<T> = delegate.clone()

        override fun execute(): Response<T> = delegate.execute()

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun cancel() = delegate.cancel()

        override fun isCanceled(): Boolean = delegate.isCanceled

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()
    }
}