package com.dvm.navigation.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Destination(val route: String = "") : Parcelable {

    @Parcelize
    object Splash : Destination("splash")

    @Parcelize
    object Main : Destination("main")

    @Parcelize
    object Menu : Destination("menu")

    @Parcelize
    object Search : Destination("search")

    @Parcelize
    object Favorite : Destination("favorite")

    @Parcelize
    object Orders : Destination("orders")

    @Parcelize
    object Ordering : Destination("ordering")

    @Parcelize
    object Registration : Destination("registration")

    @Parcelize
    object FinishRegister : Destination()

    @Parcelize
    object PasswordRestoration : Destination("restoration")

    @Parcelize
    object Profile : Destination("profile")

    @Parcelize
    object Cart : Destination("cart")

    @Parcelize
    object Notification : Destination("notification")

    @Parcelize
    object Back : Destination()

    @Parcelize
    object LoginTarget : Destination()

    @Parcelize
    data class BackToOrdering(val address: String) : Destination()

    @Parcelize
    class Map : Destination(ROUTE) {
        companion object {
            const val ROUTE = "map"
            const val MAP_ADDRESS = "map_back_address_result"
        }
    }

    @Parcelize
    data class Login(
        val targetDestination: Destination? = null
    ) : Destination(ROUTE) {
        companion object {
            const val ROUTE = "login"
        }
    }

    @Parcelize
    data class Dish(
        val dishId: String
    ) : Destination(ROUTE) {
        fun createRoute(dishId: String) = "dish/$dishId"
        companion object {
            const val ROUTE = "dish/{dishId}"
            const val DISH_ID = "dishId"
        }
    }

    @Parcelize
    data class Order(
        val orderId: String
    ) : Destination(ROUTE) {
        fun createRoute(orderId: String) = "order/$orderId"
        companion object {
            const val ROUTE = "order/{orderId}"
            const val ORDER_ID = "orderId"
        }
    }

    @Parcelize
    data class Category(
        val categoryId: String,
        val subcategoryId: String? = null
    ) : Destination(ROUTE) {

        fun createRoute(
            categoryId: String,
            subcategoryId: String?
        ) =
            "category/$categoryId/?subcategoryId=$subcategoryId"

        companion object {
            const val ROUTE = "category/{categoryId}/?subcategoryId={subcategoryId}"
            const val CATEGORY_ID = "categoryId"
            const val SUBCATEGORY_ID = "subcategoryId"
        }
    }
}