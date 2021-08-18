package com.dvm.yammydelivery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dvm.menu.menu.MenuFragmentDirections
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.navigation.api.model.MAP_ADDRESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class NavigationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    navigator: Navigator
) : ViewModel() {

    var navController: NavController? = null

    private val currentDestination
        get() = navController?.currentBackStackEntry?.destination?.id

    private var targetDestination: Destination?
        get() = savedStateHandle.get(TARGET_DESTINATION)
        set(value) = savedStateHandle.set(TARGET_DESTINATION, value)

    init {
        navigator.destination
            .onEach { destination ->
                val navController = navController ?: return@onEach

                if (destination is Destination.Login && destination.targetDestination != null) {
                    targetDestination = destination.targetDestination
                    navigateTo(
                        navController = navController,
                        destination = destination,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(currentDestination!!, true)
                            .build()
                    )
                } else {
                    if (
                        destination !is Destination.Login &&
                        destination !is Destination.Register &&
                        destination !is Destination.PasswordRestore &&
                        destination !is Destination.LoginTarget
                    ) {
                        targetDestination = null
                    }
                    navigateTo(
                        navController = navController,
                        destination = destination
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun navigateToNotification() {
        navController?.let {
            navigateTo(it, Destination.Notification)
        }
    }

    private fun navigateTo(
        navController: NavController,
        destination: Destination,
        navOptions: NavOptions? = null
    ) {
        when (destination) {
            Destination.Main -> {
                if (currentDestination == R.id.splashFragment) {
                    navController.popBackStack()
                }
                navController.navigate(MainGraphDirections.toMain(), navOptions)
            }
            Destination.Menu -> {
                navController.navigate(MainGraphDirections.toMenu(), navOptions)
            }
            Destination.Search -> {
                navController.navigate(MenuFragmentDirections.toSearch(), navOptions)
            }
            is Destination.Category -> {
                navController.navigate(
                    MenuFragmentDirections.toCategory(
                        categoryId = destination.categoryId,
                        subcategoryId = destination.subcategoryId
                    ),
                    navOptions
                )
            }
            is Destination.Dish -> {
                navController.navigate(MainGraphDirections.toDish(destination.dishId), navOptions)
            }
            Destination.Favorite -> {
                navController.navigate(MainGraphDirections.toFavorite(), navOptions)
            }
            Destination.Cart -> {
                navController.navigate(MainGraphDirections.toCart(), navOptions)
            }
            Destination.Ordering -> {
                navController.navigate(MainGraphDirections.toOrdering(), navOptions)
            }
            is Destination.BackToOrdering -> {
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(MAP_ADDRESS, destination.address)
                navController.popBackStack()
            }
            Destination.Orders -> {
                navController.navigate(MainGraphDirections.toOrders(), navOptions)
            }
            is Destination.Order -> {
                navController.navigate(
                    MainGraphDirections.toOrder(destination.orderId),
                    if (currentDestination == R.id.orderingFragment) {
                        NavOptions.Builder()
                            .setPopUpTo(currentDestination!!, true)
                            .build()
                    } else {
                        null
                    }
                )
            }
            is Destination.Login -> {
                navController.navigate(MainGraphDirections.toLogin(), navOptions)
            }
            Destination.Register -> {
                navController.navigate(MainGraphDirections.toRegister(), navOptions)
            }
            Destination.FinishRegister -> {
                navController.popBackStack(R.id.loginFragment, true)
            }
            Destination.PasswordRestore -> {
                navController.navigate(MainGraphDirections.toRestorePassword(), navOptions)
            }
            Destination.Profile -> {
                navController.navigate(MainGraphDirections.toProfile(), navOptions)
            }
            Destination.Notification -> {
                navController.navigate(MainGraphDirections.toNotifications(), navOptions)
            }
            Destination.Map -> {
                navController.navigate(MainGraphDirections.toMap())
            }
            Destination.Back -> {
                navController.navigateUp()
            }
            is Destination.LoginTarget -> {
                val targetDestination = targetDestination
                if (targetDestination != null) {
                    navigateTo(
                        navController = navController,
                        destination = targetDestination,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(currentDestination!!, true)
                            .build()
                    )
                } else {
                    navController.navigateUp()
                }
            }
        }
    }

    companion object {
        private const val TARGET_DESTINATION = "targetDestination"
    }
}
