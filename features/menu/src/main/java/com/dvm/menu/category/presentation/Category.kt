package com.dvm.menu.category.presentation

import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
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

@ExperimentalFoundationApi
@Composable
fun Category(
    state: CategoryState,
    onAction: (CategoryAction) -> Unit
) {
    Column {

        var selectedColor by remember {
            mutableStateOf(AccentColors.values().random().color)
        }

        CategoryAppBar(
            selectedSort = if (state is CategoryState.Data) state.selectedSort else null,
            selectedColor = selectedColor,
            onAction = onAction
        )
        when (state) {
            is CategoryState.Data -> {
                CategoryContent(
                    title = state.title,
                    subcategories = state.subcategories,
                    dishes = state.dishes,
                    selectedCategoryId = state.selectedCategoryId,
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it },
                    onSubcategoryClick = { onAction(CategoryAction.SubcategoryClick(it)) },
                    onDishClick = { onAction(CategoryAction.AddToCartClick(it)) },
                    onFavoriteClick = { onAction(CategoryAction.AddToFavoriteClick(it))}
                )
            }
            is CategoryState.Error -> {
            }
            CategoryState.Loading -> {
            }
        }
    }
}

@Composable
private fun CategoryAppBar(
    selectedSort: SortType?,
    selectedColor: Color,
    onAction: (CategoryAction) -> Unit
) {
    Spacer(Modifier.statusBarsHeight())

    TopAppBar(
        title = { },
        navigationIcon = {
            AppBarIconBack(onNavigateUp = { onAction(CategoryAction.NavigateUpClick) })
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {

            var showDropdownMenu by remember { mutableStateOf(false) }

            DropdownMenu(
                expanded = showDropdownMenu,
                onDismissRequest = { showDropdownMenu = false }
            ) {
                SortType.values().forEach { type ->
                    DropdownMenuItem(
                        onClick = {
                            onAction(CategoryAction.SortPick(type))
                            showDropdownMenu = false
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
                    .clickable(
                        onClick = { showDropdownMenu = true }
                    )
            )
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun CategoryContent(
    title: String,
    subcategories: List<Subcategory>,
    dishes: List<Dish>,
    selectedCategoryId: String?,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit,
    onFavoriteClick: (dishId: String) -> Unit
) {
    Column {

        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(start = 20.dp, bottom = 15.dp)
        )

        if (subcategories.isNotEmpty()) {
            SubcategoryTabs(
                subcategories = subcategories,
                selectedTabIndex = subcategories.indexOfFirst { it.id == selectedCategoryId },
                onColorSelected = onColorSelected,
                onSubcategoryClick = onSubcategoryClick
            )
        } else {
            Divider(Modifier.padding(horizontal = 15.dp))
            Divider(
                color = selectedColor.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            Divider(Modifier.padding(horizontal = 15.dp))
        }


        val animatableColor = remember { Animatable(Color.White) }
        LaunchedEffect(selectedColor) {
            animatableColor.animateTo(selectedColor, animationSpec = tween(durationMillis = 1000))
        }

        Crossfade(targetState = dishes) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalGradient(animatableColor.value.copy(alpha = 0.2f))  // TODO color
            ) {
                items(2) { Spacer(Modifier.height(15.dp)) }

                items(dishes) { dish ->
                    DishItem(
                        dish = dish,
                        onClick = onDishClick,
                        onFavoriteClick = onFavoriteClick
                    )
                }

                items(2) { Spacer(Modifier.navigationBarsHeight()) }
            }
        }
    }
}

@Composable
private fun SubcategoryTabs(
    subcategories: List<Subcategory>,
    selectedTabIndex: Int,
    onColorSelected: (Color) -> Unit,
    onSubcategoryClick: (subcategoryId: String) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {},
        modifier = Modifier,
        backgroundColor = MaterialTheme.colors.background
    ) {

        val colors = remember { AccentColors.values().toList().shuffled() }
        val colorSize = colors.size

        subcategories.forEachIndexed { index, subcategory ->

            val color = remember { colors[index % colorSize].color }
            val selected = index == selectedTabIndex
            if (selected) onColorSelected(color)

            Tab(
                selected = selected,
                onClick = { onSubcategoryClick(subcategory.id) }
            ) {
                Surface(
                    shape = RoundedCornerShape(percent = 50),  // TODO
                    color = color.copy(alpha = 0.1f),   // TODO
                    border = if (selected) BorderStroke(
                        width = 1.dp,
                        color = color
                    ) else null,
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 4.dp,
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
    onClick: (dishId: String) -> Unit,
    onFavoriteClick: (dishId: String) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp),
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
            var selected by remember{ mutableStateOf(false)}  // temp
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
