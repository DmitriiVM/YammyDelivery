package com.dvm.yammydelivery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.dvm.auth.api.CartScreen
import com.dvm.auth.api.LoginScreen
import com.dvm.auth.api.MapScreen
import com.dvm.auth.api.NotificationScreen
import com.dvm.auth.api.OrderScreen
import com.dvm.auth.api.OrderingScreen
import com.dvm.auth.api.OrdersScreen
import com.dvm.auth.api.PasswordRestoreScreen
import com.dvm.auth.api.ProfileScreen
import com.dvm.auth.api.RegistrationScreen
import com.dvm.dish.api.DishScreen
import com.dvm.menu.api.CategoryScreen
import com.dvm.menu.api.FavoriteScreen
import com.dvm.menu.api.MainScreen
import com.dvm.menu.api.MenuScreen
import com.dvm.menu.api.SearchScreen
import com.dvm.navigation.api.model.Destination
import com.dvm.splash.api.SplashScreen

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.Splash.route
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

        composable("${Destination.Dish.ROUTE}/{${Destination.Dish.DISH_ID}}") { DishScreen() }
        composable("${Destination.Order.ROUTE}/{${Destination.Order.ORDER_ID}}") { OrderScreen() }

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
        ) {
            CategoryScreen()
        }
    }
}