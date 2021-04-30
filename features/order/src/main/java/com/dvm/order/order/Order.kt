package com.dvm.order.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.navigation.Navigator
import com.dvm.order.R
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.ui.components.*
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Order(
    state: OrderState,
    navigator: Navigator,
    onEvent: (OrderEvent) -> Unit,
) {
    Drawer(
        navigator = navigator
    ) {
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

            val order = state.order

            // TODO different color text
            Text("Статус: ${order?.status?.name}")
            Text("Сумма: ${order?.total}")
            Text("Адрес: ${order?.address}")
            Text("Дата: ${order?.createdAt}")

            OutlinedButton(
                enabled = order?.completed == true || state.order?.status?.cancelable == true,
                onClick = {
                    if (order?.completed == true) {
                        onEvent(OrderEvent.ReorderClick)
                    } else {
                        onEvent(OrderEvent.CancelOrder)
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (order?.completed == true) {
                    Text(text = "Повторить заказ")
                } else {
                    Text(text = "Отменить заказ")
                }

            }

            Text("Состав заказа")
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(order?.items.orEmpty()) { item ->
                    Row(Modifier.fillMaxWidth()) {
                        CoilImage(
                            data = item.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                        Column(Modifier.fillMaxWidth()) {
                            Text(item.name)
                            Row(Modifier.fillMaxWidth()) {
                                Text(item.amount.toString())
                                Text(item.price.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.networkCall) {
        LoadingScrim()
    }

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(OrderEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }

    if (!state.actionAlertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(OrderEvent.DismissAlert) }
        Alert(
            message = state.actionAlertMessage,
            onDismiss = onDismiss,
            buttons = {
                AlertButton(
                    text = { Text("Нет") },
                    onClick = onDismiss
                )
                AlertButton(
                    text = { Text("Да") },
                    onClick = { onEvent(OrderEvent.OrderAgain) }
                )
            }
        )
    }
}