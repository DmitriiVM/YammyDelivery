package com.dvm.yammydelivery

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.dvm.auth.api.*
import com.dvm.dish.api.DishScreen
import com.dvm.menu.api.*
import com.dvm.navigation.api.model.Destination
import com.dvm.order.api.MapScreen
import com.dvm.order.api.OrderScreen
import com.dvm.order.api.OrderingScreen
import com.dvm.order.api.OrdersScreen
import com.dvm.splash.api.SplashScreen

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.route) { SplashScreen() }
        composable(Destination.Main.route) { MainScreen() }
        composable(Destination.Menu.route) { MenuScreen() }
        composable(Destination.Search.route) { SearchScreen() }
        composable(Destination.Favorite.route) { FavoriteScreen() }
        composable(Destination.Login.ROUTE) { LoginScreen() }
        composable(Destination.Registration.route) { RegistrationScreen() }
        composable(Destination.PasswordRestoration.route) { PasswordRestoreScreen() }
        composable(Destination.Cart.route) { CartScreen() }
        composable(Destination.Notification.route) { NotificationScreen() }
        composable(Destination.Ordering.route) { OrderingScreen(navController) }
        composable(Destination.Orders.route) { OrdersScreen() }
        composable(Destination.Profile.route) { ProfileScreen() }
        composable(Destination.Map.ROUTE) { MapScreen() }

        composable("${Destination.Dish.ROUTE}/{${Destination.Dish.DISH_ID}}") { entry ->
            val dishId = entry.arguments?.getString(Destination.Dish.DISH_ID)
            DishScreen(requireNotNull(dishId))
        }
        composable("${Destination.Order.ROUTE}/{${Destination.Order.ORDER_ID}}") { entry ->
            val orderId = entry.arguments?.getString(Destination.Order.ORDER_ID)
            OrderScreen(requireNotNull(orderId))
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
            CategoryScreen(arguments)
        }
    }
}