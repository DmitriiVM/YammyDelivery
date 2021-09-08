package com.dvm.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.cart.model.CartEvent
import com.dvm.cart.model.CartState
import com.dvm.db.api.models.CartItemDetails
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.DefaultAppBar
import com.dvm.ui.components.EmptyPlaceholder
import com.dvm.ui.components.Image
import com.dvm.ui.components.LoadingScrim
import com.dvm.utils.DrawerItem
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getStateViewModel

@Composable
internal fun Cart(
    viewModel: CartViewModel = getStateViewModel()
) {
    val state: CartState = viewModel.state
    val onEvent: (CartEvent) -> Unit = { viewModel.dispatch(it) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.onViewAppeared()
    }

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.CART
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            DefaultAppBar(
                title = {
                    Text(stringResource(R.string.cart_appbar_title))
                },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                },
            )

            if (state.items.isEmpty()) {
                EmptyPlaceholder(
                    resId = R.raw.empty_image,
                    text = stringResource(R.string.cart_message_info_empty_cart)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    LazyColumn(Modifier.weight(1f)) {
                        itemsIndexed(state.items) { index, item ->
                            CartItem(
                                item = item,
                                onDishClick = { onEvent(CartEvent.OpenDish(item.dishId)) },
                                onAddPiece = { onEvent(CartEvent.AddPiece(item.dishId)) },
                                onRemovePiece = { onEvent(CartEvent.RemovePiece(item.dishId)) },
                            )
                            if (index != state.items.lastIndex) {
                                Divider()
                            }
                        }
                        item {
                            PromoCode(
                                promoCode = state.promoCode,
                                appliedPromoCode = state.appliedPromoCode,
                                promoCodeDescription = state.promoCodeText,
                                onValueChange = { onEvent(CartEvent.ChangePromoCode(it)) },
                                onApplyPromoCode = { onEvent(CartEvent.ApplyPromoCode) },
                                onCancelPromoCode = { onEvent(CartEvent.CancelPromoCode) }
                            )
                        }
                    }
                    BottomContent(
                        totalPrice = state.totalPrice,
                        onClick = { onEvent(CartEvent.CreateOrder) }
                    )
                }
            }
        }
    }

    if (!state.alert.isNullOrEmpty()) {
        val onDismiss = { onEvent(CartEvent.DismissAlert) }
        Alert(
            message = state.alert,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }

    if (state.progress) {
        LoadingScrim()
    }
}

@Composable
private fun CartItem(
    item: CartItemDetails,
    onDishClick: (String) -> Unit,
    onAddPiece: () -> Unit,
    onRemovePiece: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 16.dp)
            .height(90.dp)
            .clickable { onDishClick(item.dishId) }) {
        Image(
            data = item.image,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            QuantityButton(
                quantity = item.quantity.toString(),
                onPlusClick = onAddPiece,
                onMinusClick = onRemovePiece
            )
        }
        Text(
            text = stringResource(
                R.string.dish_item_price,
                item.price
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
private fun QuantityButton(
    quantity: String,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextButton(
            onClick = onMinusClick,
            modifier = Modifier
                .size(35.dp)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp)
                )
        ) {
            Text("-")
        }
        Text(
            text = quantity,
            modifier = Modifier
                .size(50.dp, 35.dp)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colors.primary
                )
                .wrapContentSize(),
        )
        TextButton(
            onClick = onPlusClick,
            modifier = Modifier
                .size(35.dp)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp)
                )
        ) {
            Text("+")
        }
    }
}

@Composable
private fun PromoCode(
    promoCode: String,
    appliedPromoCode: Boolean,
    promoCodeDescription: String,
    onValueChange: (String) -> Unit,
    onApplyPromoCode: () -> Unit,
    onCancelPromoCode: () -> Unit
) {
    Row {
        TextField(
            value = promoCode,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = !appliedPromoCode,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )
        Button(
            onClick = {
                if (appliedPromoCode) {
                    onCancelPromoCode()
                } else {
                    onApplyPromoCode()
                }
            },
            enabled = promoCode.trim().isNotEmpty(),
            modifier = Modifier.align(Alignment.Bottom)
        ) {
            if (appliedPromoCode) {
                Text(stringResource(R.string.cart_button_cancel_promocode))
            } else {
                Text(stringResource(R.string.cart_button_apply_promocode))
            }
        }
    }
    if (appliedPromoCode) {
        Text(promoCodeDescription)
    }
}

@Composable
private fun BottomContent(
    totalPrice: Int,
    onClick: () -> Unit
) {
    Column(Modifier.navigationBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.cart_total_price))
            Text(
                text = stringResource(
                    R.string.dish_item_price,
                    totalPrice
                )
            )
        }
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cart_button_create_order))
        }
    }
}
