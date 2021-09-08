package com.dvm.notifications

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notificationModule = module {

    viewModel {
        NotificationViewModel(
            notificationRepository = get(),
            navigator = get(),
            datastore = get()
        )
    }
}