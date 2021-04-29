package com.dvm.order.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dvm.appmenu.Drawer
import com.dvm.navigation.Navigator
import com.dvm.order.R
import com.dvm.order.orders.model.OrderStatus
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Ordering(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit,
    navigator: Navigator,
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        navigator = navigator
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.orders_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch { drawerState.open() }
                    }
                },
            )

            TabRow(selectedTabIndex = state.status.ordinal) {
                Tab(
                    text = { Text("Актуальные") },
                    selected = state.status == OrderStatus.ACTUAL,
                    onClick = { onEvent(OrdersEvent.SelectStatus(OrderStatus.ACTUAL)) },
                )
                Tab(
                    text = { Text("Выполненные") },
                    selected = state.status == OrderStatus.COMPLETED,
                    onClick = { onEvent(OrdersEvent.SelectStatus(OrderStatus.COMPLETED)) },
                )

            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(state.orders){ order ->
                    Column(
                        Modifier.clickable { onEvent(OrdersEvent.OrderClick(order.id)) }
                    ) {
                        Text(order.createdAt.toString())
                        Row {
                            Text(order.id)
                            Text(order.total.toString())
                        }
                        Row {
                            Text(order.address)
                            Text(order.status)
                        }
                        Divider()
                    }
                }
            }
        }
    }
}