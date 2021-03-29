package com.dvm.dish.dish_impl.launcher

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.dvm.dish.dish_api.DishLauncher
import com.dvm.dish.dish_impl.presentation.DishFragment
import javax.inject.Inject

class DefaultDishLauncher @Inject constructor(): DishLauncher{
    override fun launch(
        dishId: String,
        @IdRes containerViewId: Int,
        fragmentManager: FragmentManager
    ){
        fragmentManager.commit {
            addToBackStack("DishFeature")
            replace(containerViewId, DishFragment.newInstance(dishId))
        }
    }
}