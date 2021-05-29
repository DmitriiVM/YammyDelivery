package com.dvm.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.cart.model.CartEvent
import com.dvm.cart.model.CartState
import com.dvm.db.api.models.CartItemDetails
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@Composable
internal fun Cart(
    state: CartState,
    onEvent: (CartEvent) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.CART
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.cart_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                },
            )

            if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.cart_message_info_empty_cart),
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        itemsIndexed(state.items){ index, item ->
                            CartItem(
                                item = item,
                                onDishClick = { onEvent(CartEvent.DishClick(item.dishId)) },
                                onAddPiece = { onEvent(CartEvent.AddPiece(item.dishId)) },
                                onRemovePiece = { onEvent(CartEvent.RemovePiece(item.dishId)) },
                            )
                            if (index != state.items.lastIndex){
                                Divider()
                            }
                        }
                        item {
                            PromoCode(
                                promoCode = state.promoCode,
                                appliedPromoCode = state.appliedPromoCode,
                                promoCodeDescription = state.promoCodeText,
                                onValueChange = { onEvent(CartEvent.PromoCodeTextChanged(it)) },
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

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(CartEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }

    if (state.networkCall) {
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
        CoilImage(
            data = item.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
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
            text = item.price.toString(),
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
    Column(modifier = Modifier.navigationBarsWithImePadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.cart_total_price))
            Text(totalPrice.toString())
        }
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cart_button_create_order))
        }
    }
}
