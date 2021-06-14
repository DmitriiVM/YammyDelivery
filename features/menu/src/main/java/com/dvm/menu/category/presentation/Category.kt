package com.dvm.menu.category.presentation

import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.db.api.models.Subcategory
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.ui.DishItem
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.verticalGradient
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

private val AppBarHeight = 56.dp

@Composable
internal fun Category(
    state: CategoryState,
    onEvent: (CategoryEvent) -> Unit,
) {
    Drawer(selected = DrawerItem.MENU) {
        val lazyListState = rememberLazyListState()
        val titleHeight = remember { mutableStateOf(0) }
        var offset by remember { mutableStateOf(0) }
        var selectedColor by rememberSaveable {
            mutableStateOf(DecorColors.values().random())
        }

        if (lazyListState.firstVisibleItemIndex == 0) {
            val maxTabOffset =
                with(LocalDensity.current) { AppBarHeight.toPx() }.toInt() + titleHeight.value
            offset = -lazyListState.firstVisibleItemScrollOffset.coerceAtMost(maxTabOffset)
        }

        CategoryContent(
            state = state,
            selectedColor = selectedColor.color,
            onColorSelected = { selectedColor = it },
            lazyListState = lazyListState,
            offset = offset,
            titleHeight = titleHeight,
            onSubcategoryClick = { onEvent(CategoryEvent.ChangeSubcategory(it)) },
            onDishClick = { onEvent(CategoryEvent.DishClick(it)) },
            onAddToCartClick = { onEvent(CategoryEvent.AddToCart(it)) },
        )

        Column {
            Spacer(Modifier.statusBarsHeight())
            CategoryAppBar(
                selectedOrder = state.orderType,
                selectedColor = selectedColor.color,
                offset = offset,
                onEvent = onEvent
            )
        }

        state.alertMessage?.let {
            Alert(
                message = state.alertMessage,
                onDismiss = { onEvent(CategoryEvent.DismissAlert) }
            ) {
                AlertButton(onClick = { onEvent(CategoryEvent.DismissAlert) })
            }
        }
    }
}

@Composable
private fun CategoryContent(
    state: CategoryState,
    selectedColor: Color,
    lazyListState: LazyListState,
    offset: Int,
    titleHeight: MutableState<Int>,
    onColorSelected: (DecorColors) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: (dishId: String) -> Unit
) {
    Box {
        val backgroundColor = MaterialTheme.colors.surface
        val animatableColor = remember { Animatable(backgroundColor) }

        LaunchedEffect(selectedColor) {
            animatableColor.animateTo(
                targetValue = selectedColor,
                animationSpec = tween(durationMillis = 1000)
            )
        }

        DishList(
            state = state,
            offset = offset,
            lazyListState = lazyListState,
            titleHeight = titleHeight,
            animatableColor = animatableColor,
            selectedColor = selectedColor,
            onDishClick = onDishClick,
            onAddToCartClick = onAddToCartClick
        )

        val subcategories = state.subcategories
        if (subcategories.isNotEmpty()) {
            Column {
                Spacer(Modifier.statusBarsHeight())
                Spacer(Modifier.height(AppBarHeight))
                val titleHeightDp = with(LocalDensity.current) { titleHeight.value.toDp() }
                Spacer(Modifier.height(titleHeightDp))
                val selectedTabIndex =
                    subcategories.indexOfFirst { it.id == state.selectedId }
                SubcategoryTabs(
                    subcategories = subcategories,
                    selectedTabIndex = selectedTabIndex,
                    offset = offset,
                    onColorSelected = onColorSelected,
                    onSubcategoryClick = onSubcategoryClick
                )
            }
        }
    }
}

@Composable
private fun DishList(
    state: CategoryState,
    offset: Int,
    lazyListState: LazyListState,
    titleHeight: MutableState<Int>,
    selectedColor: Color,
    animatableColor: Animatable<Color, AnimationVector4D>,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: (dishId: String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradient(animatableColor.value.copy(alpha = 0.15f))
    ) {

        val itemWidth = with(LocalDensity.current) { (constraints.maxWidth / 2).toDp() - 5.dp }

        Crossfade(state.dishes) {
            LaunchedEffect(it) {
                lazyListState.scrollToItem(0, -offset)
            }

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
            ) {
                item {
                    DishListHeader(
                        state = state,
                        titleHeight = titleHeight,
                        selectedColor = selectedColor
                    )
                }

                val chunkedDishes = state.dishes.chunked(2)
                items(chunkedDishes) { dishes ->
                    Row(Modifier.fillMaxWidth()) {
                        dishes.forEach { dish ->
                            DishItem(
                                dish = dish,
                                modifier = Modifier
                                    .width(itemWidth)
                                    .padding(5.dp),
                                onDishClick = onDishClick,
                                onAddToCartClick = onAddToCartClick
                            )
                        }
                    }
                }
                item { Spacer(Modifier.navigationBarsHeight()) }
            }
        }
    }
}

@Composable
private fun DishListHeader(
    state: CategoryState,
    selectedColor: Color,
    titleHeight: MutableState<Int>
) {
    Spacer(Modifier.statusBarsHeight())
    Spacer(Modifier.height(AppBarHeight))
    Text(
        text = state.title,
        style = MaterialTheme.typography.h2,
        modifier = Modifier
            .onSizeChanged { size -> titleHeight.value = size.height }
            .padding(start = 20.dp, bottom = 15.dp)
    )

    if (state.subcategories.isNotEmpty()) {
        Spacer(Modifier.height(AppBarHeight))
    } else {
        Divider(Modifier.padding(horizontal = 8.dp))
        Divider(
            color = selectedColor.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(Modifier.padding(horizontal = 8.dp))
    }
    Spacer(Modifier.height(15.dp))
}

@Composable
private fun CategoryAppBar(
    selectedOrder: OrderType?,
    selectedColor: Color,
    offset: Int,
    onEvent: (CategoryEvent) -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            AppBarIconBack(
                modifier = Modifier
                    .graphicsLayer(
                        translationX = offset.toFloat(),
                        alpha = 1f + offset * 0.03f
                    ),
                onNavigateUp = { onEvent(CategoryEvent.BackClick) }
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {

            var expanded by remember { mutableStateOf(false) }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                OrderType.values().forEach { type ->
                    DropdownMenuItem(
                        onClick = {
                            onEvent(CategoryEvent.OrderBy(type))
                            expanded = false
                        }
                    ) {
                        Text(
                            text = stringResource(type.stringResource),
                            color = if (type == selectedOrder) {
                                selectedColor
                            } else {
                                Color.Unspecified
                            }
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable(onClick = { expanded = true })
                    .graphicsLayer(
                        translationX = -offset.toFloat(),
                        alpha = 1f + offset * 0.03f
                    )
            )
        }
    )
}

@Composable
private fun SubcategoryTabs(
    subcategories: List<Subcategory>,
    selectedTabIndex: Int,
    offset: Int,
    onColorSelected: (DecorColors) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {},
        modifier = Modifier.graphicsLayer(translationY = offset.toFloat()),
        backgroundColor = MaterialTheme.colors.background
    ) {

        val colors = remember { DecorColors.values().toList().shuffled() }

        subcategories.forEachIndexed { index, subcategory ->

            val color = remember { colors[index % colors.size] }
            val selected = index == selectedTabIndex
            if (selected) onColorSelected(color)

            Tab(
                selected = selected,
                onClick = { onSubcategoryClick(subcategory.id) }
            ) {
                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = color.color.copy(alpha = 0.15f),
                    border = if (selected) {
                        BorderStroke(
                            width = 1.dp,
                            color = color.color.copy(alpha = 0.5f)
                        )
                    } else {
                        null
                    },
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                ) {
                    Text(
                        text = subcategory.name,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }
        }
    }
}