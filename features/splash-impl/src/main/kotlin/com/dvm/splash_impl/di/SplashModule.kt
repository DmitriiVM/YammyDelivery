package com.dvm.splash_impl

import com.dvm.splash_api.SplashNavHost
import com.dvm.splash_impl.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel {
        SplashViewModel(
            updateService = get(),
            datastore = get(),
            navigator = get()
        )
    }

    factory<SplashNavHost> {
        DefaultSplashNavHost()
    }
}