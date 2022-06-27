package com.dvm.drawer_api

import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import com.dvm.utils.DrawerItem

interface DrawerLauncher {

    @Composable
    fun Drawer(
        drawerState: DrawerState,
        selected: DrawerItem,
        content: @Composable () -> Unit
    )
}