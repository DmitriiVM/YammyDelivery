package com.dvm.menu_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dvm.menu_api.MenuNavHost
import com.dvm.menu_impl.presentation.category.presentation.CategoryScreen
import com.dvm.menu_impl.presentation.dish.DishScreen
import com.dvm.menu_impl.presentation.favorite.FavoriteScreen
import com.dvm.menu_impl.presentation.main.MainScreen
import com.dvm.menu_impl.presentation.menu.MenuScreen
import com.dvm.menu_impl.presentation.search.SearchScreen
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.getString

internal class DefaultMenuNavHost : MenuNavHost {

    override fun addComposables(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {

            composable(Destination.Main.route) {
                MainScreen()
            }

            composable(Destination.Menu.route) {
                MenuScreen()
            }

            composable(Destination.Search.route) {
                SearchScreen()
            }

            composable(Destination.Favorite.route) {
                FavoriteScreen()
            }

            composable(Destination.Dish.ROUTE) { entry ->
                DishScreen(entry.getString(Destination.Dish.DISH_ID))
            }

            composable(
                route = Destination.Category.ROUTE,
                arguments = listOf(
                    navArgument(Destination.Category.SUBCATEGORY_ID) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                CategoryScreen(it.arguments)
            }
        }
    }
}