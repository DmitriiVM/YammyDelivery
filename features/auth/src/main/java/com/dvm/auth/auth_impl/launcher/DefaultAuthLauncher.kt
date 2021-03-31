package com.dvm.auth.auth_impl.launcher

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.dvm.auth.auth_api.AuthLauncher
import javax.inject.Inject

internal class DefaultAuthLauncher @Inject constructor(): AuthLauncher {
    override fun launch(
        containerViewId: Int,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.commit {
            addToBackStack("AuthFeature")
            replace(containerViewId, AuthNavHostFragment())
        }
    }
}