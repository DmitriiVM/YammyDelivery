package com.dvm.menu.category.presentation

import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.db.api.data.models.Subcategory
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.ui.DishItem
import com.dvm.navigation.Navigator
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.verticalGradient
import com.dvm.ui.themes.AccentColors
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

private val AppBarHeight = 56.dp

@ExperimentalFoundationApi
@Composable
internal fun Category(
    state: CategoryState,
    onEvent: (CategoryEvent) -> Unit,
    navigator: Navigator,
) {
    Drawer(navigator = navigator) {
        val lazyListState = rememberLazyListState()
        val titleHeight = remember { mutableStateOf(0) }
        var offset by remember { mutableStateOf(0) }
        // TODO rememberSaveable
        var selectedColor by remember {
            mutableStateOf(AccentColors.values().random().color)
        }

        if (lazyListState.firstVisibleItemIndex == 0) {
            val maxTabOffset =
                with(LocalDensity.current) { AppBarHeight.toPx() }.toInt() + titleHeight.value
            offset = -lazyListState.firstVisibleItemScrollOffset.coerceAtMost(maxTabOffset)
        }

        CategoryContent(
            state = state,
            selectedColor = selectedColor,
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
                selectedOrder = state.selectedOrder,
                selectedColor = selectedColor,
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


@ExperimentalFoundationApi
@Composable
private fun CategoryContent(
    state: CategoryState,
    selectedColor: Color,
    lazyListState: LazyListState,
    offset: Int,
    titleHeight: MutableState<Int>,
    onColorSelected: (Color) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: (dishId: String) -> Unit
) {
    Box {
        val color = remember { Animatable(Color.White) }

        LaunchedEffect(selectedColor) {
            color.animateTo(
                targetValue = selectedColor,
                animationSpec = tween(durationMillis = 1000)
            )
        }

        DishList(
            color = color,
            state = state,
            lazyListState = lazyListState,
            offset = offset,
            titleHeight = titleHeight,
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
                    subcategories.indexOfFirst { it.id == state.selectedCategoryId }
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
    color: Animatable<Color, AnimationVector4D>,
    state: CategoryState,
    lazyListState: LazyListState,
    offset: Int,
    titleHeight: MutableState<Int>,
    selectedColor: Color,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: (dishId: String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradient(color.value.copy(alpha = 0.2f))
            .padding(8.dp)
    ) {

        val itemWidth = with(LocalDensity.current) { (constraints.maxWidth / 2).toDp() }

        Crossfade(state.dishes) {
            LaunchedEffect(it) {
                lazyListState.scrollToItem(0, -offset)
            }

            LazyColumn(state = lazyListState) {
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
                                modifier = Modifier.width(itemWidth),
                                onDishClick = onDishClick,
                                onAddToCartClick = onAddToCartClick
                            )
                        }
                    }
                }
                if (chunkedDishes.size < 3) {
                    item {
                        Spacer(modifier = Modifier.height(1000.dp))
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
    titleHeight: MutableState<Int>,
    selectedColor: Color
) {
    Spacer(Modifier.statusBarsHeight())
    Spacer(modifier = Modifier.height(AppBarHeight))
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
                            text = type.title,
                            color = if (type == selectedOrder) selectedColor else Color.Unspecified
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
    onColorSelected: (Color) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {},
        modifier = Modifier.graphicsLayer(translationY = offset.toFloat()),
        backgroundColor = MaterialTheme.colors.background
    ) {

        val colors = remember { AccentColors.values().toList().shuffled() }

        subcategories.forEachIndexed { index, subcategory ->

            val color = remember { colors[index % colors.size].color }
            val selected = index == selectedTabIndex
            if (selected) onColorSelected(color)

            Tab(
                selected = selected,
                onClick = { onSubcategoryClick(subcategory.id) }
            ) {
                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = color.copy(alpha = 0.1f),
                    border = if (selected) BorderStroke(
                        width = 1.dp,
                        color = color
                    ) else null,
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