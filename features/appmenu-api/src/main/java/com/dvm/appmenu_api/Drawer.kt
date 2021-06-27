package com.dvm.appmenu_api

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import com.dvm.appmenu.AppDrawer
import com.dvm.utils.DrawerItem

// It's not the way how we usually handle features api.
// Usually we have interface here.
// But the main point is to prevent feature dependency on another feature.
// In this scenario we don't need to use some class from app menu feature,
// but just to invoke a composable function.
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