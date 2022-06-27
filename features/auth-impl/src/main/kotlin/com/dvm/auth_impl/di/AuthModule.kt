package com.dvm.auth_impl.di

import com.dvm.auth_api.AuthNavHost
import com.dvm.auth_impl.DefaultAuthNavHost
import com.dvm.auth_impl.data.network.DefaultAuthApi
import com.dvm.auth_impl.domain.AuthApi
import com.dvm.auth_impl.domain.AuthInteractor
import com.dvm.auth_impl.domain.DefaultAuthInteractor
import com.dvm.auth_impl.presentation.login.LoginViewModel
import com.dvm.auth_impl.presentation.register.RegisterViewModel
import com.dvm.auth_impl.presentation.restore.PasswordRestoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel {
        LoginViewModel(
            authInteractor = get(),
            navigator = get(),
            savedState = get()
        )
    }

    viewModel {
        RegisterViewModel(
            authInteractor = get(),
            datastore = get(),
            profileInteractor = get(),
            updateService = get(),
            navigator = get(),
            savedState = get()
        )
    }

    viewModel {
        PasswordRestoreViewModel(
            authInteractor = get(),
            navigator = get(),
            savedState = get()
        )
    }

    factory<AuthApi> {
        DefaultAuthApi(
            client = get()
        )
    }

    factory<AuthInteractor> {
        DefaultAuthInteractor(
            authApi = get(),
            profileInteractor = get(),
            datastore = get(),
            updateService = get()
        )
    }

    factory<AuthNavHost> {
        DefaultAuthNavHost()
    }
}