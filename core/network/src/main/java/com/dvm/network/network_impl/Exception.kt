package com.dvm.network.network_impl

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

internal suspend fun <T> api(call: suspend CoroutineScope.() -> T): T =
    coroutineScope {
        try {
            call.invoke(this)
        } catch (exception: Exception) {
            Log.d("mmm", " :  api --  $exception")
            throw exception.wrap()
        }
    }

private fun Exception.wrap(): Exception =
    when (this) {
        is HttpException -> {
            when (this.code()) {
                400 -> Exception("Некорректный запрос")
//                402 -> Exception("Пользователь не найден")
                else -> Exception("Что-то пошло не так. Мы уже работаем над этим.")
            }
        }
        is SocketTimeoutException -> Exception("Ошибка. Попробуйте позже")
        is IOException -> Exception("Нет доступа к интернету. Попробуйте позже")
        else -> Exception("Что-то пошло не так. Мы уже работаем над этим.")
    }
