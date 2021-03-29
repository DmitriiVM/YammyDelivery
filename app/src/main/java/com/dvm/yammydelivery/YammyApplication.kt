package com.dvm.yammydelivery

import android.app.Application
import com.dvm.db.db_impl.di.DatabaseComponentHolder
import com.dvm.yammydelivery.di.DaggerAppComponent
import kotlinx.coroutines.*

class YammyApplication: Application() {

    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        DatabaseComponentHolder.init(appComponent.databaseDependencies())
        scope.launch {
//            UpdateService().update(applicationContext)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        scope.cancel()
    }
}