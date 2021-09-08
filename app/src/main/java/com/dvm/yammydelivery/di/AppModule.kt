package com.dvm.yammydelivery.di

import com.dvm.utils.AppLauncher
import com.dvm.yammydelivery.DefaultAppLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {

    factory<AppLauncher> {
        DefaultAppLauncher()
    }

    factory {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}