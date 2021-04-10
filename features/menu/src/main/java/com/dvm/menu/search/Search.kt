package com.dvm.menu.search

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.navigation.Navigator
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Search(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigator: Navigator,
) {
    Drawer(navigator = navigator) {

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                navigationIcon = {

                    AppBarIconBack(onNavigateUp = { onEvent(SearchEvent.BackClick) })
                }
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { onEvent(SearchEvent.QueryChange(it)) },
                    modifier = Modifier
                )
                IconButton(onClick = { }) {
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
                categoryId = category.id,
                onCategoryClick = { onEvent(SearchEvent.CategoryClick(it)) }
            )
        }
        items(state.subcategories) { subcategory ->
            SearchCategoryItem(
                name = subcategory.name,
                categoryId = subcategory.id,
                onCategoryClick = { onEvent(SearchEvent.SubcategoryClick(it)) }
            )
        }
        items(state.dishes) { dish ->
            DishItem(
                dish = dish,
                onDishClick = { onEvent(SearchEvent.SubcategoryClick(it)) },
                onAddToCartClick = { onEvent(SearchEvent.AddToCart(it)) },
                onFavoriteClick = { onEvent(SearchEvent.AddToFavorite(it)) }
            )
        }
    }

//                Crossfade(targetState = state.dishes) {
//                    LazyColumn(modifier = Modifier.fillMaxSize()){
//                        items(state.categories){ categories ->
//
//                        }
//                        items(state.subcategories){ subcategories ->
//
//                        }
//                        items(state.dishes){ dishes ->
//
//                        }
//                    }
//                }
}

@Composable
fun SearchCategoryItem(
    name: String,
    categoryId: String,
    onCategoryClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCategoryClick(categoryId)
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
