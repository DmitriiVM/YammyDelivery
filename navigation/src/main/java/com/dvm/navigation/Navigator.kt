package com.dvm.navigation

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    var navigationTo: ((Destination) -> Unit)? = null
    var currentDestination: Destination? = null
        private set

    fun back() = navigationTo?.invoke(Destination.Back)
    fun goTo(destination: Destination) {
        currentDestination = destination
        navigationTo?.invoke(destination)
    }
}

sealed class Destination {
    data class Category(val id: String) : Destination()
    data class Dish(val id: String) : Destination()
    data class Order(val id: String) : Destination()
    object Main : Destination()
    object Menu : Destination()
    object Search : Destination()
    object Favorite : Destination()
    object Orders : Destination()
    object Ordering : Destination()
    object Auth : Destination()
    object Register : Destination()
    object PasswordRestore : Destination()
    object PasswordChange : Destination()
    object Profile : Destination()
    object Cart : Destination()
    object Notification : Destination()
    object Back : Destination()
}