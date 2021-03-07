package com.dvm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBarIconMenu(
    onAppMenuClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Outlined.Menu,
        contentDescription = null,
        modifier = Modifier
            .padding(start = 10.dp)
            .clickable(onClick = onAppMenuClick)
    )
}

@Composable
fun AppBarIconBack(
    onNavigateUp: () -> Unit
) {
    Icon(
        imageVector = Icons.Outlined.ArrowBack,
        contentDescription = null,
        modifier = Modifier
            .padding(end = 10.dp)
            .clickable(onClick = onNavigateUp)
    )
}

@Composable
fun AppBarIconSearch(
    onSearchClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = null,
        modifier = Modifier
            .padding(end = 10.dp)
            .clickable(onClick = onSearchClick)
    )
}