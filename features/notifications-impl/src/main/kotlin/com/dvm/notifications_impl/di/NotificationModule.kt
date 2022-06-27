package com.dvm.notifications_impl.di

import com.dvm.notifications_api.NotificationNavHost
import com.dvm.notifications_api.domain.NotificationInteractor
import com.dvm.notifications_impl.DefaultNotificationNavHost
import com.dvm.notifications_impl.NotificationDatabase
import com.dvm.notifications_impl.data.database.DefaultNotificationRepository
import com.dvm.notifications_impl.domain.DefaultNotificationInteractor
import com.dvm.notifications_impl.domain.NotificationRepository
import com.dvm.notifications_impl.presentation.NotificationViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notificationModule = module {

    viewModel {
        NotificationViewModel(
            interactor = get(),
            navigator = get(),
            datastore = get()
        )
    }

    factory<NotificationInteractor> {
        DefaultNotificationInteractor(
            repository = get()
        )
    }

    factory<NotificationRepository> {
        DefaultNotificationRepository(
            notificationQueries = get<NotificationDatabase>().notificationQueries
        )
    }

    single {
        NotificationDatabase(
            AndroidSqliteDriver(
                schema = NotificationDatabase.Schema,
                context = get(),
                name = "NotificationDatabase"
            )
        )
    }

    factory<NotificationNavHost> {
        DefaultNotificationNavHost()
    }
}