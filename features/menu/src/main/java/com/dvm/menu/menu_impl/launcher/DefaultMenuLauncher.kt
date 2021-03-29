package com.dvm.menu.menu_impl.launcher

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.dvm.menu.menu_api.MenuLauncher
import com.dvm.utils.di.FeatureScope
import javax.inject.Inject

@FeatureScope
internal class DefaultMenuLauncher @Inject constructor(): MenuLauncher {
    override fun launch(
        containerViewId: Int,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.commit {
            addToBackStack("MenuFeature")
            replace(containerViewId, NavHostFragment())
        }
    }
}