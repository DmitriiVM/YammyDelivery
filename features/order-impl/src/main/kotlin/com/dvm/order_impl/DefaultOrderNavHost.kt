package com.dvm.order_impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dvm.navigation.api.model.Destination
import com.dvm.order_api.OrderNavHost
import com.dvm.order_impl.presentation.map.MapScreen
import com.dvm.order_impl.presentation.order.OrderScreen
import com.dvm.order_impl.presentation.ordering.OrderingScreen
import com.dvm.order_impl.presentation.orders.OrdersScreen
import com.dvm.utils.BackStackValueObserver
import com.dvm.utils.getString

internal class DefaultOrderNavHost : OrderNavHost {

    override fun addComposables(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.apply {

            composable(Destination.Order.ROUTE) { entry ->
                OrderScreen(entry.getString(Destination.Order.ORDER_ID))
            }

            composable(
                route = Destination.Orders.route
            ) {
                OrdersScreen()
            }

            composable(
                route = Destination.Map.ROUTE
            ) {
                MapScreen()
            }

            composable(Destination.Ordering.route) {
                var address by remember { mutableStateOf("") }
                navController.BackStackValueObserver<String>(Destination.Map.MAP_ADDRESS) {
                    address = it
                }
                OrderingScreen(address)
            }
        }
    }
}