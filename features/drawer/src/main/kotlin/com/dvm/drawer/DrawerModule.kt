package com.dvm.drawer

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val drawerModule = module {

    viewModel {
        DrawerViewModel(
            datastore = get(),
            profileRepository = get(),
            favoriteRepository = get(),
            orderRepository = get(),
            cartRepository = get(),
            notificationRepository = get(),
            navigator = get()
        )
    }
}