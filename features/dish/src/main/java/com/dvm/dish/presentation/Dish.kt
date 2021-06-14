package com.dvm.dish.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvm.appmenu_api.Drawer
import com.dvm.dish.R
import com.dvm.dish.presentation.model.DishEvent
import com.dvm.dish.presentation.model.DishState
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.DefaultAppBar
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@OptIn(ExperimentalStdlibApi::class, ExperimentalComposeApi::class)
@Composable
internal fun Dish(
    state: DishState,
    onEvent: (DishEvent) -> Unit,
) {
    Drawer(selected = DrawerItem.NONE) {

        val dish = state.dish ?: return@Drawer
        val color = remember { DecorColors.values().random().color }

        LazyColumn {
            item {
                Column(Modifier.fillMaxSize()) {

                    Spacer(
                        Modifier
                            .statusBarsHeight()
                            .background(color.copy(alpha = 0.3f))
                    )

                    Box(Modifier.fillMaxWidth()) {

                        ImageHeader(
                            color = color,
                            image = dish.image
                        )

                        DishAppBar(
                            color = color,
                            isFavorite = dish.isFavorite,
                            onNavigateUp = { onEvent(DishEvent.BackClick) },
                            onFavoriteClick = { onEvent(DishEvent.ToggleFavorite) }
                        )
                    }

                    Text(
                        text = dish.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 50.dp, bottom = 40.dp),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = dish.description.orEmpty(),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 20.dp)
                    )

                    PurchaseSection(
                        price = dish.price,
                        oldPrice = dish.oldPrice,
                        quantity = state.quantity,
                        onRemoveItem = { onEvent(DishEvent.RemovePiece) },
                        onAddItem = { onEvent(DishEvent.AddPiece) },
                        onAddToCart = { onEvent(DishEvent.AddToCart) },
                    )

                    Divider(Modifier.padding(top = 20.dp))

                    ReviewHeader(
                        rating = dish.rating,
                        color = color
                    )
                }
            }

            items(dish.reviews) { review ->
                ReviewItem(
                    review = review,
                    color = color
                )
            }
        }
    }

    state.alertMessage?.let {
        Alert(
            message = state.alertMessage,
            onDismiss = { onEvent(DishEvent.DismissAlert) }
        ) {
            AlertButton(onClick = { onEvent(DishEvent.DismissAlert) })
        }
    }
}


@Composable
private fun DishAppBar(
    isFavorite: Boolean,
    color: Color,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    DefaultAppBar(
        navigationIcon = {
            AppBarIconBack(onNavigateUp = onNavigateUp)
        },
        actions = {
            IconButton(
                modifier = Modifier,
                onClick = onFavoriteClick
            ) {
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = color
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                    )
                }
            }
        }
    )
}


@Composable
private fun ImageHeader(color: Color, image: String) {

    val horizontalPointOffset = 50f
    val verticalPointOffset = 50f

    Canvas(Modifier.fillMaxWidth()) {

        drawRect(
            color = color.copy(alpha = 0.3f),
            topLeft = Offset.Zero,
            size = Size(width = size.width, height = 450f)
        )

        pointGrid(
            startY = 170f,
            lines = 3,
            color = color,
            width = size.width,
            verticalOffset = verticalPointOffset,
            horizontalOffset = horizontalPointOffset,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 90.dp),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            data = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .border(width = 1.dp, color = Color.Gray)
        )
    }

    Canvas(Modifier.fillMaxWidth()) {
        pointGrid(
            startY = 990f,
            lines = 2,
            width = size.width,
            verticalOffset = verticalPointOffset,
            horizontalOffset = horizontalPointOffset,
            color = color,
        )
    }
}


@Composable
private fun PurchaseSection(
    price: Int,
    oldPrice: Int,
    quantity: Int,
    onRemoveItem: () -> Unit,
    onAddItem: () -> Unit,
    onAddToCart: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        if (oldPrice > price) {
            Text(
                text = stringResource(R.string.dish_item_price, oldPrice),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                fontSize = 16.sp,
                textDecoration = TextDecoration.LineThrough
            )
            Text(
                text = stringResource(R.string.dish_item_price, price),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
        } else {
            Text(
                text = stringResource(R.string.dish_item_price, price),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
            )
        }

        QuantityButton(
            color = MaterialTheme.colors.onSurface.copy(
                alpha = ButtonDefaults.OutlinedBorderOpacity
            ),
            quantity = quantity,
            onMinusClick = onRemoveItem,
            onPlusClick = onAddItem
        )
    }

    OutlinedButton(
        onClick = onAddToCart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(stringResource(R.string.dish_button_add_to_cart))
    }
}


@Composable
private fun QuantityButton(
    quantity: Int,
    color: Color,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(36.dp)
            .border(
                width = 1.dp,
                color = color,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        val spacerModifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(color)

        val textModifier = Modifier
            .fillMaxHeight()
            .width(36.dp)
            .wrapContentHeight()

        Text(
            text = "-",
            modifier = Modifier
                .clickable(onClick = onMinusClick)
                .then(textModifier),
            textAlign = TextAlign.Center
        )
        Spacer(spacerModifier)
        Text(
            text = quantity.toString(),
            modifier = textModifier,
            textAlign = TextAlign.Center
        )
        Spacer(spacerModifier)
        Text(
            text = "+",
            modifier = Modifier
                .clickable(onClick = onPlusClick)
                .then(textModifier),
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalStdlibApi::class)
private fun DrawScope.pointGrid(
    startY: Float,
    lines: Int,
    width: Float,
    verticalOffset: Float,
    horizontalOffset: Float,
    color: Color
) {
    repeat(lines) { index ->
        pointLine(
            y = startY + verticalOffset * index,
            width = width,
            offset = horizontalOffset,
            color = color
        )
    }
}


@OptIn(ExperimentalStdlibApi::class)
private fun DrawScope.pointLine(
    y: Float,
    width: Float,
    offset: Float,
    color: Color
) {
    val offsets = buildList {
        repeat((width / offset).toInt() + 1) { index ->
            add(
                Offset(
                    x = index * offset + 15,
                    y = y
                )
            )
        }
    }
    drawPoints(
        points = offsets,
        pointMode = PointMode.Points,
        color = color,
        strokeWidth = 20f,
        cap = StrokeCap.Round
    )
}