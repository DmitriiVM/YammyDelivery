package com.dvm.drawer

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.database.api.CartRepository
import com.dvm.database.api.FavoriteRepository
import com.dvm.database.api.NotificationRepository
import com.dvm.database.api.OrderRepository
import com.dvm.database.api.ProfileRepository
import com.dvm.drawer.model.DrawerEvent
import com.dvm.drawer.model.DrawerState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.DrawerItem
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

@HiltViewModel
internal class DrawerViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(DrawerState())
        private set

    private lateinit var hiltEntryPoint: AppViewModelEntryPoint

    private val datastore get() = hiltEntryPoint.datastore()
    private val profileRepository get() = hiltEntryPoint.profileRepository()
    private val favoriteRepository get() = hiltEntryPoint.favoriteRepository()
    private val orderRepository get() = hiltEntryPoint.orderRepository()
    private val cartRepository get() = hiltEntryPoint.cartRepository()
    private val notificationRepository get() = hiltEntryPoint.notificationRepository()
    private val navigator get() = hiltEntryPoint.navigator()

    fun init(context: Context) {
        hiltEntryPoint =
            EntryPointAccessors.fromApplication(context, AppViewModelEntryPoint::class.java)

        combine(
            profileRepository.profile(),
            notificationRepository.count(),
            cartRepository.totalQuantity()
        ) { profile, notificationCount, totalQuantity ->
            state = if (profile != null) {
                state.copy(
                    name = "${profile.firstName} ${profile.lastName}",
                    email = profile.email,
                    cartQuantity = totalQuantity ?: 0,
                    newNotificationCount = notificationCount
                )
            } else {
                state.copy(
                    name = "",
                    email = "",
                    cartQuantity = totalQuantity ?: 0,
                    newNotificationCount = 0
                )
            }
        }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: DrawerEvent) {
        when (event) {
            is DrawerEvent.SelectItem -> {
                viewModelScope.launch {
                    when (event.item) {
                        DrawerItem.MAIN -> navigator.goTo(Destination.Main)
                        DrawerItem.MENU -> navigator.goTo(Destination.Menu)
                        DrawerItem.FAVORITE -> navigator.goTo(Destination.Favorite)
                        DrawerItem.CART -> navigator.goTo(Destination.Cart)
                        DrawerItem.PROFILE -> navigator.goTo(Destination.Profile)
                        DrawerItem.ORDERS -> navigator.goTo(Destination.Orders)
                        DrawerItem.NOTIFICATION -> navigator.goTo(Destination.Notification)
                        DrawerItem.NONE -> { }
                    }
                }
            }
            DrawerEvent.Auth -> {
                viewModelScope.launch {
                    if (datastore.isAuthorized()) {
                        state = state.copy(
                            alert = CoreR.string.app_menu_message_logout
                        )
                    } else {
                        hiltEntryPoint.navigator().goTo(Destination.Login())
                    }
                }
            }
            DrawerEvent.Logout -> {
                state = state.copy(alert = null)
                viewModelScope.launch {
                    datastore.deleteAccessToken()
                    profileRepository.deleteProfile()
                    favoriteRepository.deleteFavorites()
                    orderRepository.deleteOrders()
                    cartRepository.clearCart()
                }
            }
            DrawerEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppViewModelEntryPoint {
        fun navigator(): Navigator
        fun datastore(): DatastoreRepository
        fun profileRepository(): ProfileRepository
        fun favoriteRepository(): FavoriteRepository
        fun orderRepository(): OrderRepository
        fun cartRepository(): CartRepository
        fun notificationRepository(): NotificationRepository
    }
}