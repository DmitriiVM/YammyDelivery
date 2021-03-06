package com.dvm.menu.category.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dvm.menu.category.temp.CategoryAction
import com.dvm.menu.category.temp.CategoryState
import com.dvm.menu.category.temp.SortType

@ExperimentalFoundationApi
@Composable
fun Category(
    state: CategoryState,
    onAction: (CategoryAction) -> Unit
) {
    Column {
        TopAppBar(
            title = { Text(text = "Dishes") },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        onClick = { onAction(CategoryAction.NavigateUpClick) }
                    )
                )
            },
            actions = {
                DropdownMenu(
                    expanded = state is CategoryState.Data && state.showSort,
                    onDismissRequest = { onAction(CategoryAction.SortClick(false)) }
                ) {

                    SortType.values().forEach {
                        DropdownMenuItem(onClick = {
                            onAction(CategoryAction.SortPick(it))
                        }) {
                            Text(text = it.title)
                        }
                    }


                }

                Icon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        onClick = {
                            onAction(CategoryAction.SortClick(true))
                        }
                    )
                )


            }
        )


        when (state) {
            is CategoryState.Data -> {

                Column {
                    val data = remember(state) { state }
                    if (data.subcategories.isNotEmpty()) {
                        ScrollableTabRow(selectedTabIndex = 0, modifier = Modifier) {
                            data.subcategories.forEach {
                                Tab(
                                    selected = true,
                                    onClick = {
                                        onAction( CategoryAction.SubcategoryClick(it.id) )
                                    }) {
                                    Text(text = it.name)
                                }
                            }
                        }
                    }


                    LazyVerticalGrid(cells = GridCells.Fixed(2)) {

                        items(data.dishes) {
                            Text(
                                text = it.name,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            Text(
                                text = it.rating.toString(),
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                    }
                }

            }
            is CategoryState.Error -> {
            }
            CategoryState.Loading -> {
            }
        }

    }
}