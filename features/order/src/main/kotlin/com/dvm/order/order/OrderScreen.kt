package com.dvm.order.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.dvm.appmenu_api.Drawer
import com.dvm.database.OrderItem
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import com.dvm.utils.extensions.format
import com.google.accompanist.insets.statusBarsHeight
import org.koin.androidx.compose.getStateViewModel
import org.koin.core.parameter.parametersOf
import com.dvm.ui.R as CoreR

@Composable
internal fun OrderScreen(
    orderId: String,
    viewModel: OrderViewModel = getStateViewModel { parametersOf(orderId) }
) {
    val state: OrderState = viewModel.state
    val onEvent: (OrderEvent) -> Unit = { viewModel.dispatch(it) }

    Drawer(selected = DrawerItem.ORDERS) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())

            state.order?.let { order ->
                DefaultAppBar(
                    title = {
                        Text(
                            stringResource(
                                CoreR.string.order_appbar_title,
                                order.createdAt.time.toString().take(5)
                            )
                        )
                    },
                    navigationIcon = {
                        AppBarIconBack {
                            onEvent(OrderEvent.Back)
                        }
                    },
                )

                Column(
                    Modifier
                        .padding(15.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    OrderField(
                        prependText = stringResource(CoreR.string.order_field_status),
                        text = order.status.name
                    )
                    OrderField(
                        prependText = stringResource(CoreR.string.order_field_total),
                        text = stringResource(
                            CoreR.string.order_price,
                            order.total
                        )
                    )
                    OrderField(
                        prependText = stringResource(CoreR.string.order_field_address),
                        text = order.address
                    )
                    OrderField(
                        prependText = stringResource(CoreR.string.order_field_date),
                        text = order.createdAt.format()
                    )

                    OutlinedButton(
                        enabled = order.completed || order.status.cancelable,
                        onClick = {
                            if (order.completed) {
                                onEvent(OrderEvent.TryOrderAgain)
                            } else {
                                onEvent(OrderEvent.CancelOrder)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        if (order.completed) {
                            Text(stringResource(CoreR.string.order_button_order_again))
                        } else {
                            Text(stringResource(CoreR.string.order_button_cancel_order))
                        }
                    }

                    Text(
                        text = stringResource(CoreR.string.order_order_content),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    order.items.fastForEachIndexed { index, item ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .size(80.dp)
                        ) {
                            OrderItem(item)
                        }
                        if (index != order.items.lastIndex) {
                            Divider()
                        }
                    }
                }
            }
        }
    }

    if (state.progress) {
        LoadingScrim()
    }

    if (state.alert != null) {
        val onDismiss = { onEvent(OrderEvent.DismissAlert) }
        Alert(
            message = stringResource(state.alert),
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }

    if (state.cancelMessage != null) {
        Alert(
            message = stringResource(state.cancelMessage),
            onDismiss = { onEvent(OrderEvent.CancelOrdering) },
            buttons = {
                AlertButton(
                    onClick = { onEvent(OrderEvent.CancelOrdering) }
                )
            }
        )
    }

    if (state.orderAgainMessage != null) {
        val onDismiss = { onEvent(OrderEvent.DismissAlert) }
        val resources = LocalContext.current.resources
        val message = stringResource(
            id = CoreR.string.order_message_cart_not_empty,
            resources.getQuantityString(
                com.dvm.ui.R.plurals.order_message_plural_dish,
                state.orderAgainMessage.count,
                state.orderAgainMessage.count,
            )
        )
        Alert(
            message = message,
            onDismiss = onDismiss,
            buttons = {
                AlertButton(
                    text = { Text(stringResource(CoreR.string.common_no)) },
                    onClick = onDismiss
                )
                AlertButton(
                    text = { Text(stringResource(CoreR.string.common_ok)) },
                    onClick = { onEvent(OrderEvent.OrderAgain) }
                )
            }
        )
    }
}

@Composable
private fun OrderItem(item: OrderItem) {
    Image(
        data = item.image,
        modifier = Modifier
            .padding(end = 15.dp)
            .size(80.dp)
            .clip(MaterialTheme.shapes.medium)
    )
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.body1
        )
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(
                    CoreR.string.order_amount,
                    item.amount
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(
                    CoreR.string.dish_item_price,
                    item.price
                ),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun OrderField(
    prependText: String,
    text: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(prependText)
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))) {
                append(text)
            }
        },
        modifier = Modifier.padding(vertical = 8.dp)
    )
}