package com.dvm.auth_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvm.auth_api.AuthNavHost
import com.dvm.auth_impl.presentation.login.LoginScreen
import com.dvm.auth_impl.presentation.register.RegisterScreen
import com.dvm.auth_impl.presentation.restore.PasswordRestoreScreen
import com.dvm.navigation.api.model.Destination

internal class DefaultAuthNavHost : AuthNavHost {
    override fun addComposables(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {

            composable(Destination.Login.ROUTE) {
                LoginScreen()
            }

            composable(Destination.Registration.route) {
                RegisterScreen()
            }

            composable(Destination.PasswordRestoration.route) {
                PasswordRestoreScreen()
            }
        }
    }
}