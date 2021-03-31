package com.dvm.auth.auth_api

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface AuthLauncher {
    fun launch(
        @IdRes containerViewId: Int,
        fragmentManager: FragmentManager
    )
}