package com.dvm.profile

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel {
        ProfileViewModel(
            context = get(),
            profileApi = get(),
            profileRepository = get(),
            datastore = get(),
            navigator = get()
        )
    }
}