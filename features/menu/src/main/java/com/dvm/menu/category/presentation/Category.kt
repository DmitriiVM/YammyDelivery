package com.dvm.menu.category.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dvm.db.entities.Dish
import com.dvm.menu.category.temp.CategoryAction
import com.dvm.menu.category.temp.CategoryState
import com.dvm.ui.components.AppBarIconBack
import dev.chrisbanes.accompanist.coil.CoilImage
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
            state = state  // TODO
        )

        when (state) {
            is CategoryState.Data -> {
                CategoryContent(
                    data = state,
                    onSubcategoryClick = { onAction(CategoryAction.SubcategoryClick(it))},
                    onDishClick = { onAction(CategoryAction.AddToCartClick(it))}
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
    state: CategoryState
) {
    Spacer(Modifier.statusBarsHeight())
    Row(modifier = Modifier .padding(8.dp)) {
        AppBarIconBack(onNavigateUp = { onAction(CategoryAction.NavigateUpClick) })
    }

    Divider()
    Spacer(Modifier.statusBarsHeight())

//    TopAppBar(
//        title = { Text(text = "Dishes") },
//        navigationIcon = {
//            AppBarIconBack(onNavigateUp = { onAction(CategoryAction.NavigateUpClick) })
//        },
//        actions = {
//            DropdownMenu(
//                expanded = state is CategoryState.Data && state.showSort,
//                onDismissRequest = { onAction(CategoryAction.SortClick(false)) }
//            ) {
//                SortType.values().forEach {
//                    DropdownMenuItem(
//                        // TODO select
//                        onClick = { onAction(CategoryAction.SortPick(it)) }
//                    ) {
//                        Text(text = it.title)
//                    }
//                }
//            }
//            Icon(
//                imageVector = Icons.Outlined.Sort,
//                contentDescription = null,
//                modifier = Modifier.clickable(
//                    onClick = {
//                        onAction(CategoryAction.SortClick(true))
//                    }
//                )
//            )
//        }
//    )
}

@ExperimentalFoundationApi
@Composable
private fun CategoryContent(
    data: CategoryState.Data, // TODO
    onSubcategoryClick: (subcategoryId: String) -> Unit,
    onDishClick: (dishId: String) -> Unit
) {
    Column {
        Divider(color = MaterialTheme.colors.onPrimary, thickness = 0.5.dp)
        val subcategories = data.subcategories
        if (subcategories.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = subcategories.indexOfFirst { it.id == data.selectedCategoryId },
                modifier = Modifier,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.9f)
            ) {
                subcategories.forEach { subcategory ->
                    Tab(
                        selected = subcategory.id == data.selectedCategoryId,
                        text = {
                            Text(text = subcategory.name)
                        },
                        onClick = {
                                  onSubcategoryClick(subcategory.id)
                        },
                        selectedContentColor = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        }

        val scrollState = rememberLazyListState()
//        scrollState.scrollToItem(0, 0)

        // Crossfade
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            state = scrollState
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
            val hasSpecialOffer = dish.hasSpecialOffer
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
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        }
    }
}
