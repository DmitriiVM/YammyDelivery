package com.dvm.auth.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.dvm.order.map.Map
import com.dvm.order.order.Order
import com.dvm.order.ordering.Ordering
import com.dvm.order.orders.Orders

@Composable
fun OrderScreen() {
    Order()
}

@Composable
fun OrdersScreen() {
    Orders()
}

@Composable
fun MapScreen() {
    Map()
}

@Composable
fun OrderingScreen(
    navHostController: NavHostController
) {
    Ordering(navHostController)
}
