package com.dvm.order.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dvm.appmenu.Drawer
import com.dvm.navigation.Navigator
import com.dvm.order.R
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Ordering(
    state: OrdersState,
    navigator: Navigator,
    onEvent: (OrdersEvent) -> Unit,
)  {
    Drawer(
        navigator = navigator
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.appbar_orders)) },
                navigationIcon = {
                    AppBarIconBack {
                        onEvent(OrdersEvent.BackClick)
                    }
                },
            )
        }
    }
}