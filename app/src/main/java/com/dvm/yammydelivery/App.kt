package com.dvm.yammydelivery

import android.app.Application
import kotlinx.coroutines.*

class App: Application() {

    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        scope.launch {
//            UpdateService().update(applicationContext)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        scope.cancel()
    }
}