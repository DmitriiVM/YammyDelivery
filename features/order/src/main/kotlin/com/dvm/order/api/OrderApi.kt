package com.dvm.order.api

import androidx.compose.runtime.Composable
import com.dvm.order.map.MapScreen
import com.dvm.order.order.OrderScreen
import com.dvm.order.ordering.OrderingScreen
import com.dvm.order.orders.OrdersScreen

@Composable
fun Order() {
    OrderScreen()
}

@Composable
fun Orders() {
    OrdersScreen()
}

@Composable
fun Map() {
    MapScreen()
}

@Composable
fun Ordering(address: String) {
    OrderingScreen(address)
}
