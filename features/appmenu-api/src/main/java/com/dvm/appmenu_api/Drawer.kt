package com.dvm.appmenu_api

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import com.dvm.appmenu.AppDrawer
import com.dvm.utils.DrawerItem

@Composable
fun Drawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    selected: DrawerItem,
    content: @Composable () -> Unit
) {
    AppDrawer(
        drawerState = drawerState,
        selected = selected,
        content = content,
    )
}