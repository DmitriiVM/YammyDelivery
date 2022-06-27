package com.dvm.splash_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvm.navigation.api.model.Destination
import com.dvm.splash_api.SplashNavHost
import com.dvm.splash_impl.presentation.SplashScreen

internal class DefaultSplashNavHost : SplashNavHost {

    override fun addComposables(navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.composable(Destination.Splash.route) {
            SplashScreen()
        }
    }
}