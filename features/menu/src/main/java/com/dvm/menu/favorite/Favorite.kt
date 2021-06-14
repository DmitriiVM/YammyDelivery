package com.dvm.menu.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.menu.R
import com.dvm.menu.common.ui.DishItem
import com.dvm.menu.favorite.model.FavoriteEvent
import com.dvm.menu.favorite.model.FavoriteState
import com.dvm.ui.components.*
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Favorite(
    state: FavoriteState,
    onEvent: (FavoriteEvent) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.FAVORITE
    ) {

        val color by rememberSaveable {
            mutableStateOf(DecorColors.values().random())
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalGradient(color.color.copy(alpha = 0.15f))
        ) {
            Spacer(modifier = Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.favorite_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            )

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.padding(5.dp)
            ) {
                items(state.dishes) { dish ->
                    DishItem(
                        dish = dish,
                        modifier = Modifier.padding(5.dp),
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
