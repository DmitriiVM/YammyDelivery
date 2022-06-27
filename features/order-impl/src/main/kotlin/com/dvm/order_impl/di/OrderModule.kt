package com.dvm.order_impl.di

import com.dvm.database.DateAdapter
import com.dvm.order_api.OrderNavHost
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.order_impl.DefaultOrderNavHost
import com.dvm.order_impl.OrderDatabase
import com.dvm.order_impl.data.database.DefaultOrderRepository
import com.dvm.order_impl.data.network.DefaultOrderApi
import com.dvm.order_impl.domain.DefaultOrderInteractor
import com.dvm.order_impl.domain.OrderApi
import com.dvm.order_impl.domain.OrderRepository
import com.dvm.order_impl.presentation.map.MapViewModel
import com.dvm.order_impl.presentation.order.OrderViewModel
import com.dvm.order_impl.presentation.ordering.OrderingViewModel
import com.dvm.order_impl.presentation.orders.OrdersViewModel
import com.dvm.orderdatabase.OrderDetailsEntity
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ordersModule = module {

    viewModel { params ->
        OrderViewModel(
            orderId = params.get(),
            orderInteractor = get(),
            cartInteractor = get(),
            datastore = get(),
            navigator = get(),
            savedState = get()
        )
    }

    viewModel {
        OrderingViewModel(
            orderInteractor = get(),
            datastore = get(),
            navigator = get()
        )
    }

    viewModel {
        OrdersViewModel(
            orderInteractor = get(),
            updateService = get(),
            navigator = get(),
            datastore = get(),
            savedState = get()
        )
    }

    viewModel {
        MapViewModel(
            navigator = get(),
            savedState = get()
        )
    }

    factory<OrderApi> {
        DefaultOrderApi(
            client = get()
        )
    }

    factory<OrderInteractor> {
        DefaultOrderInteractor(
            cartInteractor = get(),
            repository = get(),
            api = get(),
            datastore = get(),
        )
    }

    factory<OrderRepository> {
        DefaultOrderRepository(
            orderQueries = get<OrderDatabase>().orderDetailsQueries,
            orderItemQueries = get<OrderDatabase>().orderItemQueries,
            orderStatusQueries = get<OrderDatabase>().orderStatusQueries
        )
    }

    single {
        OrderDatabase(
            AndroidSqliteDriver(
                schema = OrderDatabase.Schema,
                context = get(),
                name = "OrderDatabase"
            ),
            orderDetailsEntityAdapter = OrderDetailsEntity.Adapter(DateAdapter)
        )
    }

    factory<OrderNavHost> {
        DefaultOrderNavHost()
    }
}