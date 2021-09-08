package com.dvm.appmenu

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val drawerModule = module {

    viewModel {
        DrawerViewModel(
            context = get(),
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