package com.dvm.dish

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dishModule = module {

    viewModel { params ->
        DishViewModel(
            _dishId = params.get(),
            context = get(),
            favoriteRepository = get(),
            cartRepository = get(),
            menuApi = get(),
            datastore = get(),
            navigator = get(),
            dishRepository = get(),
            savedState = get()
        )
    }
}