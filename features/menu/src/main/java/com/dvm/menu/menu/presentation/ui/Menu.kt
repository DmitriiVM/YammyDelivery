package com.dvm.menu.menu.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvm.menu.R
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.ui.components.AppBarIconMenu
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
        MenuHeader(
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
private fun MenuHeader(
    onAppMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(color = Color.White.copy(alpha = 0.8f))
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
    }
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
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(menuItems) { item ->
            Card(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(1f)
                    .padding(7.dp)
                    .clickable(
                        onClick = {
                            onItemClick(
                                when (item) {
                                    is MenuItem.Item -> item.id
                                    MenuItem.SpecialOffer -> "special"  // TODO think of better approach
                                }
                            )
                        }
                    ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.TwoTone.Star,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colors.primary.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = when (item) {
                            is MenuItem.Item -> item.title
                            MenuItem.SpecialOffer -> "Акции"
                        }
                    )
                }
            }
        }
    }
}