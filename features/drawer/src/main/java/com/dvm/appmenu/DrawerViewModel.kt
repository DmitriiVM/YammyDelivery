package com.dvm.appmenu

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.appmenu.model.DrawerEvent
import com.dvm.appmenu.model.DrawerState
import com.dvm.db.api.CartRepository
import com.dvm.db.api.FavoriteRepository
import com.dvm.db.api.NotificationRepository
import com.dvm.db.api.OrderRepository
import com.dvm.db.api.ProfileRepository
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.DrawerItem
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class DrawerViewModel(
    private val context : Context,
    private val datastore: DatastoreRepository,
    private val profileRepository: ProfileRepository,
    private val favoriteRepository: FavoriteRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val notificationRepository: NotificationRepository,
    private val navigator: Navigator
): ViewModel() {

    var state by mutableStateOf(DrawerState())
        private set

    init {
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
                            alert = context.getString(R.string.app_menu_message_logout)
                        )
                    } else {
                        navigator.goTo(Destination.Login())
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
}