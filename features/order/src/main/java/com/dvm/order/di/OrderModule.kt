package com.dvm.order

import com.dvm.order.map.MapViewModel
import com.dvm.order.order.OrderViewModel
import com.dvm.order.ordering.OrderingViewModel
import com.dvm.order.orders.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val orderModule = module {

    viewModel { params ->
        OrderViewModel(
            _orderId = params.get(),
            context = get(),
            orderRepository = get(),
            cartRepository = get(),
            orderApi = get(),
            datastore = get(),
            navigator = get(),
            savedState = get()
        )
    }

    viewModel {
        OrderingViewModel(
            context = get(),
            orderApi = get(),
            orderRepository = get(),
            cartRepository = get(),
            datastore = get(),
            navigator = get()
        )
    }

    viewModel {
        OrdersViewModel(
            context = get(),
            orderRepository = get(),
            updateService = get(),
            navigator = get(),
            datastore = get(),
            savedState = get()
        )
    }

    viewModel {
        MapViewModel(
            context = get(),
            navigator = get(),
            savedState = get()
        )
    }
}