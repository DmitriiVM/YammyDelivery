package com.dvm.profile_impl.di

import com.dvm.profile_api.ProfileNavHost
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.profile_impl.DefaultProfileNavHost
import com.dvm.profile_impl.ProfileDatabase
import com.dvm.profile_impl.data.database.DefaultProfileRepository
import com.dvm.profile_impl.data.network.DefaultProfileApi
import com.dvm.profile_impl.domain.DefaultProfileInteractor
import com.dvm.profile_impl.domain.ProfileApi
import com.dvm.profile_impl.domain.ProfileRepository
import com.dvm.profile_impl.presentation.ProfileViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModuleTmp = module {

    viewModel {
        ProfileViewModel(
            interactor = get(),
            navigator = get()
        )
    }

    factory<ProfileApi> {
        DefaultProfileApi(
            client = get()
        )
    }

    factory<ProfileNavHost> {
        DefaultProfileNavHost()
    }

    factory<ProfileInteractor> {
        DefaultProfileInteractor(
            repository = get(),
            api = get(),
            datastore = get(),
        )
    }

    factory<ProfileRepository> {
        DefaultProfileRepository(
            get<ProfileDatabase>().profileQueries
        )
    }

    single {
        ProfileDatabase(
            AndroidSqliteDriver(
                schema = ProfileDatabase.Schema,
                context = get(),
                name = "ProfileDatabase"
            )
        )
    }
}