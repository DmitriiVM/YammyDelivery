package com.dvm.order.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.db.api.models.OrderData
import com.dvm.order.R
import com.dvm.order.orders.model.OrderStatus
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.DefaultAppBar
import com.dvm.ui.components.LoadingScrim
import com.dvm.utils.DrawerItem
import com.dvm.utils.extensions.format
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Ordering(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.ORDERS
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.orders_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch { drawerState.open() }
                    }
                },
            )

            TabRow(
                selectedTabIndex = state.status.ordinal,
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = contentColorFor(MaterialTheme.colors.surface),
                indicator = {tabPositions ->
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.primary) {
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[state.status.ordinal])
                        )
                    }

                }
            ) {
                Tab(
                    text = { Text(stringResource(R.string.orders_tab_actual)) },
                    selected = state.status == OrderStatus.ACTUAL,
                    onClick = { onEvent(OrdersEvent.SelectStatus(OrderStatus.ACTUAL)) },
                )
                Tab(
                    text = { Text(stringResource(R.string.orders_tab_completed)) },
                    selected = state.status == OrderStatus.COMPLETED,
                    onClick = { onEvent(OrdersEvent.SelectStatus(OrderStatus.COMPLETED)) },
                )
            }
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp)
                    .navigationBarsPadding()
            ) {
                items(state.orders) { order ->
                    OrderItem(
                        order = order,
                        onOrderClick = { onEvent(OrdersEvent.OrderClick(order.id)) }
                    )
                }
            }
        }
    }

    if (state.networkCall) {
        LoadingScrim()
    }
}

@OptIn(ExperimentalComposeApi::class)
@Composable
private fun OrderItem(
    order: OrderData,
    onOrderClick: () -> Unit
) {
    Column(
        Modifier.clickable(onClick = onOrderClick)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = order.createdAt.format(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 15.dp, bottom = 8.dp)
            )
        }
        Row {
            Text(
                text = stringResource(
                    R.string.orders_order_number,
                    order.createdAt.time.toString().take(5)
                ),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(
                    R.string.dish_item_price,
                    order.total
                ),
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colors.primary
            )
        }
        Row {
            Text(
                text = order.address,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = order.status,
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        }
        Divider()
    }
}