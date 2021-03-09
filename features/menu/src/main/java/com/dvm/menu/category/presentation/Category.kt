package com.dvm.menu.category.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
        CategoryHeader(
            onAction = onAction,
            showSort = state.showSortPopup
        )
        when (state) {
            is CategoryState.Data -> {
                CategoryContent(
                    data = state,
                    onSubcategoryClick = { onAction(CategoryAction.SubcategoryClick(it)) },
                    onDishClick = { onAction(CategoryAction.AddToCartClick(it)) }
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
private fun CategoryHeader(
    onAction: (CategoryAction) -> Unit,
    showSort: Boolean
) {
    Spacer(Modifier.statusBarsHeight())
    TopAppBar(
        title = { Text(text = "Dishes") },
        navigationIcon = {
            AppBarIconBack(onNavigateUp = { onAction(CategoryAction.NavigateUpClick) })
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {
            DropdownMenu(
                expanded = showSort,
                onDismissRequest = { onAction(CategoryAction.SortClick(false)) }  // TODO local state
            ) {
                SortType.values().forEach {
                    DropdownMenuItem(
                        // TODO select state
                        onClick = { onAction(CategoryAction.SortPick(it)) }
                    ) {
                        Text(text = it.title)
                    }
                }
            }
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable(
                        onClick = {
                            onAction(CategoryAction.SortClick(true))
                        }
                    )
            )
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun CategoryContent(
    data: CategoryState.Data, // TODO
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit
) {
    Column {

        val selectedColor = remember { mutableStateOf(Color.White) }

        val subcategories = data.subcategories
        if (subcategories.isNotEmpty()) {
            val selectedTabIndex = subcategories.indexOfFirst { it.id == data.selectedCategoryId }
            SubcategoryTabs(
                subcategories = subcategories,
                selectedTabIndex = selectedTabIndex,
                selectedColor = selectedColor,
                onSubcategoryClick = onSubcategoryClick
            )
        }

        // TODO Crossfade
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalGradient(selectedColor.value.copy(alpha = 0.2f))  // TODO color
        ) {
            items(2) {
                Spacer(modifier = Modifier.height(15.dp))
            }

            items(data.dishes) { dish ->
                DishItem(
                    dish = dish,
                    onClick = onDishClick
                )
            }

            items(2) {
                Spacer(modifier = Modifier.navigationBarsHeight())
            }
        }
    }
}

@Composable
private fun SubcategoryTabs(
    subcategories: List<Subcategory>,
    selectedTabIndex: Int,
    selectedColor: MutableState<Color>,
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
            if (selected) selectedColor.value = color

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
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = subcategory.name,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DishItem(
    dish: Dish,
    onClick: (dishId: String) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            val hasSpecialOffer = dish.hasSpecialOffer  // TODO
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
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {

                    Icon(
                        imageVector = Icons.Sharp.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .offset(y = (-20).dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.secondary)
                            .clickable { onClick(dish.id) }
                    )
                }
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = dish.price.toString(),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = dish.name,
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .height(40.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
