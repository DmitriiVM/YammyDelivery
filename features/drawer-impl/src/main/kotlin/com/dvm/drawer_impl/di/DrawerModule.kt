package com.dvm.drawer_impl.di

import com.dvm.drawer_api.DrawerLauncher
import com.dvm.drawer_impl.DefaultDrawerLauncher
import com.dvm.drawer_impl.presentation.DrawerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val drawerModule = module {

    viewModel {
        DrawerViewModel(
            datastore = get(),
            profileInteractor = get(),
            favoriteInteractor = get(),
            orderInteractor = get(),
            cartInteractor = get(),
            notificationInteractor = get(),
            navigator = get()
        )
    }

    factory<DrawerLauncher> {
        DefaultDrawerLauncher()
    }
}