package com.dvm.menu.menu_api

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface MenuLauncher {
    fun launch(
        @IdRes containerViewId: Int,
        fragmentManager: FragmentManager
    )
}