package com.dvm.menu.menu.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvm.menu.R
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.themes.light_blue
import com.dvm.ui.themes.light_green
import com.dvm.ui.themes.light_violet
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@ExperimentalFoundationApi
@Composable
fun MenuView(
    menuItems: List<MenuItem>,
    onItemClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onAppMenuClick: () -> Unit
) {
    Column {
        MenuAppBar(
            onAppMenuClick = onAppMenuClick,
            onSearchClick = onSearchClick
        )
        MenuContent(
            menuItems = menuItems,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun MenuAppBar(
    onAppMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Spacer(Modifier.statusBarsHeight())
    TopAppBar(
        title = { Text(stringResource(R.string.app_bar_title_menu)) },
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
                            MenuItem.SpecialOffer -> MENU_SPECIAL_OFFER  // TODO think of better approach
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
                Image(
                    imageVector = Icons.TwoTone.Star,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colors.primary.copy( alpha = 0.5f )
                    )
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
                }
            )
        }
    }
}