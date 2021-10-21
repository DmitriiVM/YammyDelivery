package com.dvm.yammydelivery

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import com.dvm.auth.api.Login
import com.dvm.auth.api.PasswordRestoration
import com.dvm.auth.api.Registration
import com.dvm.cart.api.Cart
import com.dvm.dish.api.Dish
import com.dvm.menu.api.*
import com.dvm.navigation.api.model.Destination
import com.dvm.notifications.api.Notification
import com.dvm.order.api.Map
import com.dvm.order.api.Order
import com.dvm.order.api.Ordering
import com.dvm.order.api.Orders
import com.dvm.profile.api.Profile
import com.dvm.splash.api.Splash

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.route) { Splash() }
        composable(Destination.Main.route) { Main() }
        composable(Destination.Menu.route) { Menu() }
        composable(Destination.Search.route) { Search() }
        composable(Destination.Favorite.route) { Favorite() }
        composable(Destination.Login.ROUTE) { Login() }
        composable(Destination.Registration.route) { Registration() }
        composable(Destination.Cart.route) { Cart() }
        composable(Destination.Orders.route) { Orders() }
        composable(Destination.Profile.route) { Profile() }
        composable(Destination.Map.ROUTE) { Map() }
        composable(Destination.Ordering.route) { Ordering(navController) }
        composable(Destination.PasswordRestoration.route) { PasswordRestoration() }

        composable("${Destination.Dish.ROUTE}/{${Destination.Dish.DISH_ID}}") { entry ->
            val dishId = entry.arguments?.getString(Destination.Dish.DISH_ID)
            Dish(requireNotNull(dishId))
        }
        composable("${Destination.Order.ROUTE}/{${Destination.Order.ORDER_ID}}") { entry ->
            val orderId = entry.arguments?.getString(Destination.Order.ORDER_ID)
            Order(requireNotNull(orderId))
        }

        composable(
            route = Destination.Notification.route,
            deepLinks = listOf(navDeepLink { uriPattern = NOTIFICATION_URI })
        ) {
            Notification()
        }

        composable(
            route = "${Destination.Category.ROUTE}/" +
                    "{${Destination.Category.CATEGORY_ID}}/" +
                    "?subcategoryId={${Destination.Category.SUBCATEGORY_ID}}",
            arguments = listOf(
                navArgument(Destination.Category.SUBCATEGORY_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { entry ->
            val arguments: Bundle? = entry.arguments
            Category(arguments)
        }
    }
}

internal const val NOTIFICATION_URI = "app://com.dvm.yammydelivery"