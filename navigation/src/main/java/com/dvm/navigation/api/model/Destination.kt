package com.dvm.navigation.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Destination: Parcelable {

    @Parcelize
    data class Category(
        val categoryId: String,
        val subcategoryId: String? = null
    ) : Destination()

    @Parcelize
    data class Login(val targetDestination: Destination? = null) : Destination()
    @Parcelize
    data class Dish(val dishId: String) : Destination()
    @Parcelize
    data class Order(val orderId: String) : Destination()
    @Parcelize
    object Main : Destination()
    @Parcelize
    object Menu : Destination()
    @Parcelize
    object Search : Destination()
    @Parcelize
    object Favorite : Destination()
    @Parcelize
    object Orders : Destination()
    @Parcelize
    object Ordering : Destination()
    @Parcelize
    object Register : Destination()
    @Parcelize
    object PasswordRestore : Destination()
    @Parcelize
    object Profile : Destination()
    @Parcelize
    object Cart : Destination()
    @Parcelize
    object Notification : Destination()
    @Parcelize
    object Back : Destination()
    @Parcelize
    object LoginTarget: Destination()
}