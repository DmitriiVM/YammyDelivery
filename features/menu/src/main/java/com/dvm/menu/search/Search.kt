package com.dvm.menu.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.TransparentAppBar
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Search(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    Drawer(selected = DrawerItem.MENU) {

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                navigationIcon = {

                    AppBarIconBack(onNavigateUp = { onEvent(SearchEvent.BackClick) })
                }
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { onEvent(SearchEvent.QueryChange(it.trimStart())) },
                    modifier = Modifier
                )
                IconButton(onClick = { onEvent(SearchEvent.RemoveQueryClick) }) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null
                    )
                }
            }
            Box(Modifier.fillMaxSize()) {
                if (state.query.trim().isEmpty()) {
                    Hints(state, onEvent)
                } else {
                    SearchResult(state, onEvent)
                }
            }
        }
    }
}

@Composable
private fun Hints(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(state.hints) { hint ->
            HintItem(
                hint = hint,
                onHintClick = {
                    onEvent(SearchEvent.HintClick(hint))
                },
                onRemoveHintClick = {
                    onEvent(SearchEvent.RemoveHintClick(hint))
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResult(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(state.categories) { category ->
            SearchCategoryItem(
                name = category.name,
                onCategoryClick = {
                    onEvent(
                        SearchEvent.CategoryClick(
                            categoryId = category.id,
                            name = category.name
                        )
                    )
                }
            )
        }
        items(state.subcategories) { subcategory ->
            SearchCategoryItem(
                name = subcategory.name,
                modifier = Modifier.background(Color.Yellow),
                onCategoryClick = {
                    onEvent(
                        SearchEvent.SubcategoryClick(
                            subcategory.parent,
                            subcategory.id,
                            subcategory.name
                        )
                    )
                }
            )
        }
        items(state.dishes) { dish ->
            DishItem(
                dish = dish,
                modifier = Modifier.padding(8.dp),
                onDishClick = { onEvent(SearchEvent.DishClick(it, dish.name)) },
                onAddToCartClick = { onEvent(SearchEvent.AddToCart(it)) },
            )
        }
    }
}

@Composable
fun SearchCategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    onCategoryClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCategoryClick()
            }
    ) {
        Text(text = name)
    }
}

@Composable
fun HintItem(
    hint: String,
    onHintClick: (String) -> Unit,
    onRemoveHintClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clickable { onHintClick(hint) }
    ) {
        Text(text = hint, modifier = Modifier)
        IconButton(onClick = { onRemoveHintClick(hint) }, modifier = Modifier) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = null
            )
        }
    }
}
