package com.dvm.menu.menu.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.menu.R
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.presentation.model.MenuEvent
import com.dvm.navigation.Navigator
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.themes.light_blue
import com.dvm.ui.themes.light_green
import com.dvm.ui.themes.light_violet
import com.dvm.ui.themes.temp
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
internal fun MenuView(
    menuItems: List<MenuItem>,
    navigator: Navigator,
    onEvent: (MenuEvent) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        navigator = navigator
    ) {
        Column {
            MenuAppBar(
                onAppMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                onSearchClick = { onEvent(MenuEvent.SearchClick) }
            )
            MenuContent(
                menuItems = menuItems,
                onItemClick = { onEvent(MenuEvent.MenuItemClick(it)) }
            )
        }
    }
}

@Composable
private fun MenuAppBar(
    onAppMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Spacer(Modifier.statusBarsHeight())
    TopAppBar(
        title = { Text("Меню") },
        navigationIcon = { AppBarIconMenu(onAppMenuClick) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable(
                        onClick = onSearchClick
                    )
            )
        }
    )
    Divider()
}

@ExperimentalFoundationApi
@Composable
private fun MenuContent(
    menuItems: List<MenuItem>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {

        items(3) {
            Spacer(Modifier.height(20.dp))
        }

        itemsIndexed(menuItems) { index, item ->
            MenuItem(
                index = index,
                item = item,
                modifier = Modifier.fillParentMaxWidth(),
                onItemClick = onItemClick
            )
        }

        items(3) {
            Spacer(Modifier.navigationBarsHeight())
        }
    }
}

@Composable
private fun MenuItem(
    index: Int,
    item: MenuItem,
    modifier: Modifier,
    onItemClick: (String) -> Unit
) {
    val rowIndex = (index - index % 3) / 3
    val height = 128
    val startY = with(LocalDensity.current) { (-height * rowIndex).dp.toPx() }
    val endY = with(LocalDensity.current) { (height * 5 - (height * rowIndex)).dp.toPx() }

    Card(
        elevation = 3.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.verticalGradient(
                startY = startY,
                endY = endY,
                colors = listOf(
                    light_violet,
                    light_green,
                    light_blue
                ),
                tileMode = TileMode.Mirror
            )
        ),
        modifier = modifier
            .aspectRatio(1f)
            .padding(7.dp)
            .clickable(
                onClick = {
                    onItemClick(
                        when (item) {
                            is MenuItem.Item -> item.id
                            MenuItem.SpecialOffer -> MENU_SPECIAL_OFFER
                        }
                    )
                }
            )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Icon(
                    painter = painterResource(
                        id = when (item) {
                            is MenuItem.Item -> getIcon(item.title)
                            MenuItem.SpecialOffer -> R.drawable.icon_special_offer
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = temp.copy(alpha = 0.5f)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                text = when (item) {
                    is MenuItem.Item -> item.title
                    MenuItem.SpecialOffer -> "Акции"
                },
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}

internal fun getIcon(dishTitle: String): Int {
    return when (dishTitle) {
        "Бургеры и хот-доги" -> R.drawable.icon_hamburger
        "Пицца" -> R.drawable.icon_pizza
        "Суши и роллы" -> R.drawable.icon_sushi
        "Завтраки" -> R.drawable.icon_breakfast
        "Супы" -> R.drawable.icon_soup
        "Основное блюдо" -> R.drawable.icon_main_dish
        "Гарниры" -> R.drawable.icon_side_dish
        "Паста" -> R.drawable.icon_pasta
        "Пельмени" -> R.drawable.icon_dumpling
        "Салаты" -> R.drawable.icon_vegetables
        "Десерты" -> R.drawable.icon_dessert
        "Закуски" -> R.drawable.icon_snack
        "Блюда на гриле" -> R.drawable.icon_grill
        "Соусы" -> R.drawable.icon_sauce
        "Напитки" -> R.drawable.icon_juice
        else -> R.drawable.icon_special_offer
    }
}