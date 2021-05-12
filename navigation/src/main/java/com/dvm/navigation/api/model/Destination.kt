package com.dvm.navigation.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Destination(val private: Boolean = false): Parcelable {
    @Parcelize
    data class Category(
        val categoryId: String,
        val subcategoryId: String? = null
    ) : Destination()

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
    object Orders : Destination(true)
    @Parcelize
    object Ordering : Destination()
    @Parcelize
    object Login : Destination()
    @Parcelize
    object Register : Destination()
    @Parcelize
    object PasswordRestore : Destination()
    @Parcelize
    object Profile : Destination(true)
    @Parcelize
    object Cart : Destination()
    @Parcelize
    object Notification : Destination(true)
    @Parcelize
    object Back : Destination()
    @Parcelize
    object LoginTarget: Destination()
}