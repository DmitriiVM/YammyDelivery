package com.dvm.navigation

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator @Inject constructor() {

    var navigationTo: ((Destination) -> Unit)? = null
}

sealed class Destination{
    object Main: Destination()
    object Menu: Destination()
    object Search: Destination()
    object Favorite: Destination()
    object Orders: Destination()
    object OrderProcess: Destination()
    object Auth: Destination()
    object Register: Destination()
    object PasswordRestore: Destination()
    object PasswordChange: Destination()
    object Profile: Destination()
    object Cart: Destination()
    object Notification: Destination()
    data class Category(val id: String): Destination()
    data class Dish(val id: String): Destination()
    data class Order(val id: String): Destination()
    object Back: Destination()
}