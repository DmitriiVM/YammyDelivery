package com.dvm.profile

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel {
        ProfileViewModel(
            profileApi = get(),
            profileRepository = get(),
            datastore = get(),
            navigator = get()
        )
    }
}