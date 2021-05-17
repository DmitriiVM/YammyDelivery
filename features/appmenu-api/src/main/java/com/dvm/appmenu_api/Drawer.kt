package com.dvm.appmenu_api

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import com.dvm.appmenu.AppDrawer

@Composable
fun Drawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit
) {
    AppDrawer(
        drawerState = drawerState,
        content = content,
    )
}