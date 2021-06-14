package com.dvm.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Composable
fun AppBarIconMenu(
    onAppMenuClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(start = 10.dp),
        onClick = onAppMenuClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = null
        )
    }
}

@Composable
fun AppBarIconBack(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    IconButton(onClick = onNavigateUp) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            modifier = modifier
        )
    }
}