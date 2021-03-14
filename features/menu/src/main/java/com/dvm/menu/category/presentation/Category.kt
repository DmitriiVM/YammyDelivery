package com.dvm.menu.category.presentation

import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvm.db.entities.Dish
import com.dvm.db.entities.Subcategory
import com.dvm.menu.category.temp.CategoryAction
import com.dvm.menu.category.temp.CategoryState
import com.dvm.menu.category.temp.SortType
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.verticalGradient
import com.dvm.ui.themes.AccentColors
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

private val AppBarHeight = 56.dp

@ExperimentalFoundationApi
@Composable
fun Category(
    state: CategoryState,
    onAction: (CategoryAction) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val titleHeight = remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    var selectedColor by remember {
        mutableStateOf(AccentColors.values().random().color)
    }

    if (lazyListState.firstVisibleItemIndex == 0) {
        val maxTabOffset =
            with(LocalDensity.current) { AppBarHeight.toPx() }.toInt() + titleHeight.value
        offset = -lazyListState.firstVisibleItemScrollOffset.coerceAtMost(maxTabOffset)
    }

    Box {
        when (state) {
            is CategoryState.Data -> {
                CategoryContent(
                    data = state,
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it },
                    lazyListState = lazyListState,
                    offset = offset,
                    titleHeight = titleHeight,
                    onSubcategoryClick = { onAction(CategoryAction.SubcategoryClick(it)) },
                    onDishClick = { onAction(CategoryAction.AddToCartClick(it)) },
                    onFavoriteClick = { onAction(CategoryAction.AddToFavoriteClick(it)) }
                )
            }
            is CategoryState.Error -> {
                // TODO
            }
            CategoryState.Loading -> {
                // TODO
            }
        }

        Column {
            Spacer(Modifier.statusBarsHeight())
            CategoryAppBar(
                selectedSort = if (state is CategoryState.Data) state.selectedSort else null,
                selectedColor = selectedColor,
                offset = offset,
                onAction = onAction
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun CategoryContent(
    data: CategoryState.Data,
    selectedColor: Color,
    lazyListState: LazyListState,
    offset: Int,
    titleHeight: MutableState<Int>,
    onColorSelected: (Color) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit,
    onFavoriteClick: (dishId: String) -> Unit
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
            data = data,
            lazyListState = lazyListState,
            offset = offset,
            titleHeight = titleHeight,
            selectedColor = selectedColor,
            onDishClick = onDishClick,
            onFavoriteClick = onFavoriteClick
        )

        val subcategories = data.subcategories
        if (subcategories.isNotEmpty()) {
            Column {
                Spacer(Modifier.statusBarsHeight())
                Spacer(Modifier.height(AppBarHeight))
                val titleHeightDp = with(LocalDensity.current) { titleHeight.value.toDp() }
                Spacer(Modifier.height(titleHeightDp))
                val selectedTabIndex =
                    subcategories.indexOfFirst { it.id == data.selectedCategoryId }
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
    data: CategoryState.Data,
    lazyListState: LazyListState,
    offset: Int,
    titleHeight: MutableState<Int>,
    selectedColor: Color,
    onDishClick: (dishId: String) -> Unit,
    onFavoriteClick: (dishId: String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradient(color.value.copy(alpha = 0.2f))
            .padding(8.dp)
    ) {

        val itemWidth = with(LocalDensity.current) { (constraints.maxWidth / 2).toDp() }

        Crossfade(data.dishes) {
            LaunchedEffect(it) {
                lazyListState.scrollToItem(0, -offset)
            }

            LazyColumn(state = lazyListState) {
                item {
                    DishListHeader(
                        data = data,
                        titleHeight = titleHeight,
                        selectedColor = selectedColor
                    )
                }

                val chunkedDishes = data.dishes.chunked(2)
                items(chunkedDishes) { dishes ->
                    Row(Modifier.fillMaxWidth()) {
                        dishes.forEach { dish ->
                            DishItem(
                                dish = dish,
                                modifier = Modifier.width(itemWidth),
                                onClick = onDishClick,
                                onFavoriteClick = onFavoriteClick
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
    data: CategoryState.Data,
    titleHeight: MutableState<Int>,
    selectedColor: Color
) {
    Spacer(Modifier.statusBarsHeight())
    Spacer(modifier = Modifier.height(AppBarHeight))
    Text(
        text = data.title,
        style = MaterialTheme.typography.h2,
        modifier = Modifier
            .onSizeChanged { size -> titleHeight.value = size.height }
            .padding(start = 20.dp, bottom = 15.dp)
    )

    if (data.subcategories.isNotEmpty()) {
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
    selectedSort: SortType?,
    selectedColor: Color,
    offset: Int,
    onAction: (CategoryAction) -> Unit
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
                onNavigateUp = { onAction(CategoryAction.NavigateUpClick) }
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
                SortType.values().forEach { type ->
                    DropdownMenuItem(
                        onClick = {
                            onAction(CategoryAction.SortPick(type))
                            expanded = false
                        }
                    ) {
                        Text(
                            text = type.title,
                            color = if (type == selectedSort) selectedColor else Color.Unspecified
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

@Composable
private fun DishItem(
    dish: Dish,
    modifier: Modifier = Modifier,
    onClick: (dishId: String) -> Unit,
    onFavoriteClick: (dishId: String) -> Unit
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = 1.dp
    ) {
        Box {
            Column {
                CoilImage(
                    data = dish.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp)), // TODO material
                    error = {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primaryVariant.copy(alpha = 0.05f)
                        )
                    }
                )
                Box {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {

                        // elevation, todo
                        Icon(
                            imageVector = Icons.Sharp.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .offset(y = (-28).dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.secondary)
                                .clickable { onClick(dish.id) }
                        )
                    }
                    Column(
                        modifier = Modifier.padding(
                            start = 5.dp,
                            end = 5.dp,
                            top = 0.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Text(
                            text = "${dish.price} ₽",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp)
                        )
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                        Text(
                            text = dish.name,
                            modifier = Modifier
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .wrapContentHeight(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                        Spacer(modifier = Modifier.height(3.dp))
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                    }
                }
            }
            if (dish.hasSpecialOffer) {
                Text(
                    text = "АКЦИЯ",
                    fontSize = 10.sp,
                    letterSpacing = 2.5.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .background(
                            color = Color.Yellow.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(topEnd = 3.dp, bottomEnd = 3.dp)
                        )
                        .padding(vertical = 3.dp, horizontal = 5.dp)
                )
            }
            var selected by remember { mutableStateOf(false) }  // temp
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 12.dp)
                        .size(20.dp)
                        .selectable(selected = selected) {
                            selected = !selected
                            onFavoriteClick(dish.id)
                        },
                    tint = if (selected) MaterialTheme.colors.secondary else Color.LightGray
                )
            }
        }
    }
}
