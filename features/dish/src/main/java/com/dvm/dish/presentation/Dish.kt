package com.dvm.dish.dish_impl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvm.appmenu.Drawer
import com.dvm.db.api.data.models.DishDetails
import com.dvm.db.api.data.models.Review
import com.dvm.dish.R
import com.dvm.dish.presentation.model.DishEvent
import com.dvm.dish.presentation.model.DishState
import com.dvm.navigation.Navigator
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun Dish(
    state: DishState,
    navigator: Navigator,
    onEvent: (DishEvent) -> Unit,
) {
    Drawer(navigator = navigator) {
        Box {
            val listState = rememberLazyListState()

            DishImage(
                imageUrl = state.dish.image,
                listState = listState
            )

            LazyColumn(state = listState) {
                item {
                    DishContent(
                        state = state,
                        onAddPeace = { onEvent(DishEvent.AddPiece) },
                        onRemovePeace = { onEvent(DishEvent.RemovePiece) },
                        onAddToCart = { onEvent(DishEvent.AddToCart) },
                        onAddReview = { onEvent(DishEvent.AddReview) }
                    )
                }
                items(state.dish.reviews) { review ->
                    ReviewItem(review)
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .navigationBarsHeight()
                            .fillMaxWidth()
                            .background(Color.White)
                    )
                }
            }

            Column {
                Spacer(Modifier.statusBarsHeight())
                DishAppBar(
                    onNavigateUp = { onEvent(DishEvent.BackClick) },
                    onFavoriteClick = { onEvent(DishEvent.ToggleFavorite) },
                    checked = state.dish.isFavorite
                )
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
}

@Composable
private fun DishAppBar(
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    checked: Boolean
) {
    val modifier = Modifier
        .background(
            color = Color.White.copy(0.3f),
            shape = CircleShape
        )
        .padding(8.dp)

    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                onClick = { onNavigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = modifier
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {
            IconButton(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { onFavoriteClick() }
            ) {
                if (checked) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = null,
                        modifier = modifier,
                        tint = MaterialTheme.colors.secondary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = modifier
                    )
                }
            }
        }
    )
}

@Composable
private fun DishImage(
    imageUrl: String,
    listState: LazyListState
) {
    CoilImage(
        data = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .graphicsLayer(translationY = -listState.firstVisibleItemScrollOffset * 0.3f)
            .fillMaxWidth()
            .height(370.dp)
    )
}

@Composable
private fun DishContent(
    state: DishState,
    onAddPeace: () -> Unit,
    onRemovePeace: () -> Unit,
    onAddToCart: () -> Unit,
    onAddReview: () -> Unit
) {
    Spacer(Modifier.height(340.dp))
    Box(
        Modifier.background(
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        )
    ) {
        Column(Modifier.fillMaxWidth()) {
            DishDetails(
                dish = state.dish,
                onAddToCart = onAddToCart,
                modifier = Modifier
            )
            Spacer(
                Modifier
                    .height(20.dp)
                    .background(Color.Red)
            )
        }
        val quantityButtonOffset = with(LocalDensity.current) { 25.dp.toPx() }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(translationY = -quantityButtonOffset),
            contentAlignment = Alignment.Center
        ) {
            QuantityButton(
                quantity = state.quantity,
                onAdd = { onAddPeace() },
                onRemove = { onRemovePeace() }
            )
        }
    }
    ReviewHeader(
        rating = state.dish.rating,
        onAddReview = onAddReview
    )
}

@Composable
private fun ReviewHeader(
    rating: Double,
    onAddReview: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_review),
            contentDescription = null
        )
        Spacer(Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text("Отзывы  ★ $rating/5")
            OutlinedButton(
                onClick = { onAddReview() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text("Добавить")
            }
        }
    }
}

@Composable
private fun QuantityButton(
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .requiredHeight(50.dp)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(50)
            )
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onRemove() }) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = null,
                Modifier.size(17.dp)
            )
        }
        Text(
            text = quantity.toString(),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(onClick = { onAdd() }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                Modifier.size(17.dp)
            )
        }
    }
}

@Composable
private fun DishDetails(
    dish: DishDetails,
    onAddToCart: () -> Unit,
    modifier: Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 1.dp,
        border = BorderStroke(
            width = 0.5.dp,
            color = Color.LightGray
        )
    ) {
        Column {
            Spacer(Modifier.height(60.dp))
            Text(
                text = dish.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(20.dp))
            dish.description?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            Spacer(Modifier.height(30.dp))

            if (dish.oldPrice > dish.price) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = dish.oldPrice.toString(),
                        color = Color.Gray,
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = "${dish.price}  ₽",
                        color = Color.Red.copy(0.5f),
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            } else {
                Text(
                    text = "${dish.price}  ₽",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Spacer(Modifier.height(20.dp))
            OutlinedButton(
                onClick = { onAddToCart() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Text("В корзину")
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ReviewItem(review: Review) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Surface(
            elevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${review.author}, ${review.date}")
                    val stars = ""
                    repeat(review.rating) { stars.endsWith("★") }
                    Text(stars)
                }
                Text(review.text)
            }
        }
    }
}







