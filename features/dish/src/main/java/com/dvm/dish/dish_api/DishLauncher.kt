package com.dvm.dish.dish_api

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface DishLauncher {
    fun launch(
        dishId: String,
        @IdRes containerViewId: Int,
        fragmentManager: FragmentManager
    )
}