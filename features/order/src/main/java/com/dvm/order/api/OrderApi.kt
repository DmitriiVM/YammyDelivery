package com.dvm.auth.api

import androidx.compose.runtime.Composable
import com.dvm.order.order.Order
import com.dvm.order.ordering.Ordering
import com.dvm.order.orders.Orders

@Composable
fun OrderScreen() { Order() }

@Composable
fun OrderingScreen() { Ordering() }

@Composable
fun OrdersScreen() { Orders() }