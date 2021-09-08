package com.dvm.menu

import com.dvm.menu.category.presentation.CategoryViewModel
import com.dvm.menu.favorite.FavoriteViewModel
import com.dvm.menu.main.MainViewModel
import com.dvm.menu.menu.MenuViewModel
import com.dvm.menu.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {

    viewModel {
        MenuViewModel(
            categoryRepository = get(),
            dishRepository = get(),
            navigator = get()
        )
    }

    viewModel { params ->
        CategoryViewModel(
            context = get(),
            categoryRepository = get(),
            dishRepository = get(),
            cartRepository = get(),
            navigator = get(),
            arguments = params.get(),
            savedState = get()
        )
    }

    viewModel {
        FavoriteViewModel(
            context = get(),
            cartRepository = get(),
            navigator = get(),
            dishRepository = get()
        )
    }

    viewModel {
        MainViewModel(
            context = get(),
            datastore = get(),
            cartRepository = get(),
            navigator = get(),
            dishRepository = get()
        )
    }

    viewModel {
        SearchViewModel(
            context = get(),
            categoryRepository = get(),
            dishRepository = get(),
            hintRepository = get(),
            cartRepository = get(),
            navigator = get(),
            savedState = get()
        )
    }
}