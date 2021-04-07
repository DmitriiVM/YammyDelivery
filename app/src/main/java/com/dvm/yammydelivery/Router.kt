package com.dvm.yammydelivery

import androidx.navigation.NavController
import com.dvm.auth.login.LoginFragmentDirections
import com.dvm.menu.menu.presentation.MenuFragmentDirections
import com.dvm.navigation.Destination


class Router(
    private val navController: NavController
) {

    fun navigateTo(destination: Destination) {
        when (destination) {
            Destination.Main -> {
                navController.navigate(MainGraphDirections.toMain())
            }
            Destination.Menu -> {
                navController.navigate(MainGraphDirections.toMenu())
            }
            Destination.Search -> {
                navController.navigate(MenuFragmentDirections.toSearch())
            }
            Destination.Favorite -> {

            }
            is Destination.Category -> {
                navController.navigate(MenuFragmentDirections.toCategory(destination.id))
            }
            is Destination.Dish -> {
                navController.navigate(MainGraphDirections.toDish(destination.id))
            }

            Destination.Cart -> {
            }
            Destination.OrderProcess -> {
            }
            Destination.Orders -> {
            }
            is Destination.Order -> {
            }

            Destination.Auth -> {
                navController.navigate(MainGraphDirections.toAuth())
            }
            Destination.Register -> {
                navController.navigate(LoginFragmentDirections.toRegister())
            }
            Destination.PasswordRestore -> {
                navController.navigate(LoginFragmentDirections.toRestore())
            }
            Destination.PasswordChange -> {

            }
            Destination.Profile -> {
            }

            Destination.Notification -> {
            }
            Destination.Back -> {
                navController.navigateUp()
            }
        }
    }
}
