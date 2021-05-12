package com.dvm.menu.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dvm.appmenu.Drawer
import com.dvm.menu.R
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.favorite.model.FavoriteEvent
import com.dvm.menu.favorite.model.FavoriteState
import com.dvm.navigation.Navigator
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Favorite(
    state: FavoriteState,
    onEvent: (FavoriteEvent) -> Unit,
    navigator: Navigator,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        navigator = navigator
    ) {

        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.favorite_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            )

            LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                items(state.dishes) { dish ->
                    DishItem(
                        dish = dish,
                        onDishClick = { onEvent(FavoriteEvent.DishClick(it)) },
                        onAddToCartClick = { onEvent(FavoriteEvent.AddToCart(it, dish.name)) },
                    )
                }
            }

        }
    }

    state.alertMessage?.let {
        Alert(
            message = state.alertMessage,
            onDismiss = { onEvent(FavoriteEvent.DismissAlert) }
        ) {
            AlertButton(onClick = { onEvent(FavoriteEvent.DismissAlert) })
        }
    }
}
