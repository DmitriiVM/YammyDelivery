package com.dvm.order.order

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
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Ordering(
    state: OrderState,
    navigator: Navigator,
    onEvent: (com.dvm.order.order.model.OrderEvent) -> Unit,
)  {
    Drawer(
        navigator = navigator
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.order_appbar_title)) },
                navigationIcon = {
                    AppBarIconBack {
                        onEvent(OrderEvent.BackClick)
                    }
                },
            )
        }
    }
}