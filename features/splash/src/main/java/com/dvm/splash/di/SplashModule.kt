package com.dvm.splash

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
}