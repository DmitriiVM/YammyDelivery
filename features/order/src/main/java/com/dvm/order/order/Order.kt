package com.dvm.order.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.db.api.models.OrderItem
import com.dvm.order.R
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import com.dvm.utils.extensions.format
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Order(
    state: OrderState,
    onEvent: (OrderEvent) -> Unit,
) {
    Drawer(selected = DrawerItem.ORDERS) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())

            val order = state.order ?: return@Column

            DefaultAppBar(
                title = {
                    Text(
                        stringResource(
                            R.string.order_appbar_title,
                            order.createdAt.time.toString().take(5)
                        )
                    )
                },
                navigationIcon = {
                    AppBarIconBack {
                        onEvent(OrderEvent.BackClick)
                    }
                },
            )


            Column(
                Modifier
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                OrderField(
                    prependText = stringResource(R.string.order_field_status),
                    text = order.status.name
                )
                OrderField(
                    prependText = stringResource(R.string.order_field_total),
                    text = stringResource(
                        R.string.order_price,
                        order.total
                    )
                )
                OrderField(
                    prependText = stringResource(R.string.order_field_address),
                    text = order.address
                )
                OrderField(
                    prependText = stringResource(R.string.order_field_date),
                    text = order.createdAt.format()
                )

                OutlinedButton(
                    enabled = order.completed || order.status.cancelable,
                    onClick = {
                        if (order.completed) {
                            onEvent(OrderEvent.OrderAgainClick)
                        } else {
                            onEvent(OrderEvent.CancelOrder)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    if (order.completed) {
                        Text(stringResource(R.string.order_button_order_again))
                    } else {
                        Text(stringResource(R.string.order_button_cancel_order))
                    }
                }

                Text(
                    text = stringResource(R.string.order_order_content),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                order.items.forEachIndexed { index, item ->
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

    if (!state.cancelMessage.isNullOrEmpty()) {
        Alert(
            message = state.cancelMessage,
            onDismiss = { onEvent(OrderEvent.OrderCanceled) },
            buttons = {
                AlertButton(
                    onClick = { onEvent(OrderEvent.OrderCanceled) }
                )
            }
        )
    }

    if (!state.orderAgainMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(OrderEvent.DismissAlert) }
        Alert(
            message = state.orderAgainMessage,
            onDismiss = onDismiss,
            buttons = {
                AlertButton(
                    text = { Text(stringResource(R.string.common_no)) },
                    onClick = onDismiss
                )
                AlertButton(
                    text = { Text(stringResource(R.string.common_ok)) },
                    onClick = { onEvent(OrderEvent.OrderAgain) }
                )
            }
        )
    }
}

@Composable
private fun OrderItem(item: OrderItem) {
    CoilImage(
        data = item.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(end = 15.dp)
            .size(80.dp)
            .clip(MaterialTheme.shapes.medium),
        error = { ErrorImage() }
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
                    R.string.order_amount,
                    item.amount
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(
                    R.string.dish_item_price,
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