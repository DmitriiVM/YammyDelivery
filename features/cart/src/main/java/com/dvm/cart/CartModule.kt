package com.dvm.cart

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {

    viewModel {
        CartViewModel(
            context = get(),
            cartRepository = get(),
            cartApi = get(),
            datastore = get(),
            navigator = get(),
            savedState = get()
        )
    }
}