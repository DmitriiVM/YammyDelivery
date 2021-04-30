package com.dvm.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.cart.model.CartEvent
import com.dvm.cart.model.CartState
import com.dvm.db.db_api.data.models.CartItemDetails
import com.dvm.navigation.Navigator
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.LoadingScrim
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@Composable
internal fun Cart(
    state: CartState,
    navigator: Navigator,
    onEvent: (CartEvent) -> Unit,
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
                        .padding(10.dp)
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(state.items) { item ->
                            CartItem(
                                item = item,
                                onDishClick = { onEvent(CartEvent.DishClick(item.dishId)) },
                                onAddPiece = { onEvent(CartEvent.AddPiece(item.dishId)) },
                                onRemovePiece = { onEvent(CartEvent.RemovePiece(item.dishId)) },
                            )
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
            .padding(10.dp)
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(item.name)
            QuantityButton(
                quantity = item.quantity.toString(),
                onPlusClick = onAddPiece,
                onMinusClick = onRemovePiece
            )
        }
        Text(item.price.toString())
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
            modifier = Modifier.weight(1f),
            enabled = !appliedPromoCode,
            maxLines = 1
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
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
        Spacer(modifier = Modifier.navigationBarsHeight())
    }
}
