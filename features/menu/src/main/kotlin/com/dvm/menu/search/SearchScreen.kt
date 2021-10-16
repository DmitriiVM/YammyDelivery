package com.dvm.menu.search

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvm.appmenu_api.Drawer
import com.dvm.menu.R
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.verticalGradient
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.DrawerItem
import com.dvm.utils.asString
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsHeight

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state: SearchState = viewModel.state
    val onEvent: (SearchEvent) -> Unit = { viewModel.dispatch(it) }

    val context = LocalContext.current

    Drawer(selected = DrawerItem.MENU) {

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            SearchField(
                query = state.query,
                onBackClick = { onEvent(SearchEvent.Back) },
                onQueryChange = { onEvent(SearchEvent.ChangeQuery(it.trimStart())) },
                onRemoveQuery = { onEvent(SearchEvent.RemoveQuery) },
            )

            val color by rememberSaveable {
                mutableStateOf(DecorColors.values().random())
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .verticalGradient(color.color.copy(alpha = 0.15f))
            ) {
                if (state.query.trim().isEmpty()) {
                    Hints(state, onEvent)
                } else {
                    SearchResult(
                        state = state,
                        color = color.color,
                        onEvent = onEvent
                    )
                }
            }
        }
    }

    state.alert?.let {
        Alert(
            message = state.alert.asString(context),
            onDismiss = { onEvent(SearchEvent.DismissAlert) }
        ) {
            AlertButton(onClick = { onEvent(SearchEvent.DismissAlert) })
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onRemoveQuery: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppBarIconBack(
            modifier = Modifier.padding(end = 10.dp),
            onNavigateUp = onBackClick
        )

        val focusRequester = FocusRequester()
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        OutlinedTextField(
            value = query,
            placeholder = {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.disabled
                ) {
                    Text(stringResource(R.string.search_text_field_hint))
                }
            },
            onValueChange = { value ->
                onQueryChange(value)
            },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
        )

        IconButton(onClick = onRemoveQuery) {
            Icon(
                painter = painterResource(R.drawable.icon_cancel),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
private fun Hints(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        itemsIndexed(state.hints) { index, hint ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEvent(SearchEvent.SelectHint(hint)) }
            ) {
                Text(
                    text = hint,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = { onEvent(SearchEvent.RemoveHint(hint)) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_cancel),
                        contentDescription = null,
                        Modifier.size(12.dp)
                    )
                }
            }
            if (index != state.hints.lastIndex) {
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResult(
    state: SearchState,
    color: Color,
    onEvent: (SearchEvent) -> Unit
) {
    val configuration = LocalConfiguration.current

    val rows = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    LazyVerticalGrid(
        cells = GridCells.Fixed(rows),
        modifier = Modifier.padding(horizontal = 5.dp),
    ) {
        items(state.categories) { category ->
            SearchCategoryItem(
                name = category.name,
                color = color.copy(alpha = 0.10f),
                onCategoryClick = {
                    onEvent(
                        SearchEvent.OpenCategory(
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
                color = MaterialTheme.colors.surface,
                onCategoryClick = {
                    onEvent(
                        SearchEvent.OpenSubcategory(
                            subcategory.parent,
                            subcategory.id,
                            subcategory.name
                        )
                    )
                }
            )
        }
        val emptyItems = (state.categories.size + state.subcategories.size) % rows
        repeat(emptyItems) {
            item { /* empty */ }
        }
        items(state.dishes) { dish ->
            DishItem(
                dish = dish,
                modifier = Modifier.padding(5.dp),
                onDishClick = {
                    onEvent(SearchEvent.OpenDish(it, dish.name))
                },
                onAddToCartClick = {
                    onEvent(
                        SearchEvent.AddToCart(
                            dishId = dish.id,
                            name = dish.name
                        )
                    )
                },
            )
        }
        items(2) { Spacer(Modifier.navigationBarsPadding()) }
    }
}

@Composable
fun SearchCategoryItem(
    name: String,
    color: Color,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onCategoryClick()
            },
        shape = MaterialTheme.shapes.medium,
        backgroundColor = color
    ) {
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    }
}
