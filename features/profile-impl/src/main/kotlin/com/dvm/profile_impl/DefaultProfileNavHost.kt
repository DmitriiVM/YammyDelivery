package com.dvm.profile_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvm.navigation.api.model.Destination
import com.dvm.profile_api.ProfileNavHost
import com.dvm.profile_impl.presentation.ProfileScreen

internal class DefaultProfileNavHost : ProfileNavHost {

    override fun addComposables(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(
            route = Destination.Profile.route
        ) {
            ProfileScreen()
        }
    }
}