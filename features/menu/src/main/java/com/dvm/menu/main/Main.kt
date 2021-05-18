package com.dvm.menu.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.db.api.models.CategoryDish
import com.dvm.menu.R
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.search.model.MainEvent
import com.dvm.menu.search.model.MainState
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.TransparentAppBar
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@Composable
internal fun Main(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.MAIN
    ) {

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            ) {
                IconButton(onClick = { onEvent(MainEvent.CartClick) }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Add something",
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (state.recommended.isNotEmpty()) {
                    DishesRowHeader(
                        title = { Text(stringResource(R.string.main_recommended)) },
                        seeAllClick = { onEvent(MainEvent.SeeAllClick) }
                    )
                    LazyRow {
                        items(state.recommended) { dish ->
                            MainDishItem(
                                dish = dish,
                                onEvent = onEvent
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                if (state.best.isNotEmpty()) {
                    DishesRowHeader(
                        title = { Text(stringResource(R.string.main_best)) },
                        seeAllClick = { onEvent(MainEvent.SeeAllClick) }
                    )
                    LazyRow {
                        items(state.best) { dish ->
                            MainDishItem(
                                dish = dish,
                                onEvent = onEvent
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                DishesRowHeader(
                    title = { Text(stringResource(R.string.main_popular)) },
                    seeAllClick = { onEvent(MainEvent.SeeAllClick) }
                )
                LazyRow {
                    items(state.popular) { dish ->
                        MainDishItem(
                            dish = dish,
                            onEvent = onEvent
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.navigationBarsHeight())
            }
        }
    }

    state.alertMessage?.let {
        Alert(
            message = state.alertMessage,
            onDismiss = { onEvent(MainEvent.DismissAlert) }
        ) {
            AlertButton(onClick = { onEvent(MainEvent.DismissAlert) })
        }
    }
}

@Composable
private fun MainDishItem(
    dish: CategoryDish,
    onEvent: (MainEvent) -> Unit
) {
    DishItem(
        dish = dish,
        onDishClick = { onEvent(MainEvent.DishClick(dish.id)) },
        onAddToCartClick = { onEvent(MainEvent.AddToCart(dish.id, dish.name)) },
    )
}

@Composable
private fun DishesRowHeader(
    title: @Composable () -> Unit,
    seeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        title()
        Text(
            text = stringResource(R.string.main_see_all),
            modifier = Modifier.clickable { seeAllClick() }
        )
    }
}