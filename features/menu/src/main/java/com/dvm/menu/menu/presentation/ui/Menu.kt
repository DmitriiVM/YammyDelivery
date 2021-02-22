package com.dvm.menu.menu.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dvm.menu.menu.domain.model.MenuItem

@ExperimentalFoundationApi
@Composable
fun MenuView(
    menuItems: List<MenuItem>,
    onItemClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onAppMenuClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = { Text(text = "Menu") },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    modifier = Modifier.clickable(
                        onClick = onAppMenuClick
                    )
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    modifier = Modifier.clickable(
                        onClick = onSearchClick
                    )
                )
            }
        )
        Column(modifier = Modifier.padding(10.dp)) {

            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(cells = GridCells.Fixed(3), content = {
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
                                            is MenuItem.Item -> item.title
                                            is MenuItem.Default -> item.title
                                            MenuItem.SpecialOffer -> "Special offers"
                                        }
                                    )
                                }
                            ),
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = Color.LightGray
                    ) {

                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = when (item) {
                                    is MenuItem.Item -> item.title
                                    is MenuItem.Default -> item.title
                                    MenuItem.SpecialOffer -> "Special offers"
                                }
                            )
                        }
                    }
                }
            })
        }
    }
}