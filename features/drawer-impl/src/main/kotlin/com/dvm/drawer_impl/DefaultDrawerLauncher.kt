package com.dvm.drawer_impl

import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import com.dvm.drawer_api.DrawerLauncher
import com.dvm.drawer_impl.presentation.AppDrawer
import com.dvm.utils.DrawerItem

internal class DefaultDrawerLauncher : DrawerLauncher {

    @Composable
    override fun Drawer(
        drawerState: DrawerState,
        selected: DrawerItem,
        content: @Composable () -> Unit
    ) {
        AppDrawer(
            drawerState = drawerState,
            selected = selected,
            content = content,
        )
    }
}