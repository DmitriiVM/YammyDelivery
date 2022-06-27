package com.dvm.cart_impl.di

import com.dvm.cart_api.CartNavHost
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_impl.CartDatabase
import com.dvm.cart_impl.DefaultCartNavHost
import com.dvm.cart_impl.data.database.DefaultCartRepository
import com.dvm.cart_impl.data.network.DefaultCartApi
import com.dvm.cart_impl.domain.CartApi
import com.dvm.cart_impl.domain.CartRepository
import com.dvm.cart_impl.domain.DefaultCartInteractor
import com.dvm.cart_impl.presentation.CartViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {

    viewModel {
        CartViewModel(
            interactor = get(),
            datastore = get(),
            navigator = get(),
            savedState = get()
        )
    }

    factory<CartApi> {
        DefaultCartApi(
            client = get()
        )
    }

    factory<CartRepository> {
        DefaultCartRepository(
            get<CartDatabase>().cartQueries
        )
    }

    factory<CartInteractor> {
        DefaultCartInteractor(
            repository = get(),
            dishRepository = get(),
            api = get()
        )
    }

    single {
        CartDatabase(
            AndroidSqliteDriver(
                schema = CartDatabase.Schema,
                context = get(),
                name = "CartDatabase"
            )
        )
    }

    factory<CartNavHost> {
        DefaultCartNavHost()
    }
}