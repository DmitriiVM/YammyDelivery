package com.dvm.drawer_impl.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.drawer_impl.presentation.model.DrawerEvent
import com.dvm.drawer_impl.presentation.model.DrawerState
import com.dvm.menu_api.domain.FavoriteInteractor
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.notifications_api.domain.NotificationInteractor
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.utils.DrawerItem
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class DrawerViewModel(
    private val datastore: DatastoreRepository,
    private val profileInteractor: ProfileInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val orderInteractor: OrderInteractor,
    private val cartInteractor: CartInteractor,
    private val navigator: Navigator,
    notificationInteractor: NotificationInteractor
) : ViewModel() {

    var state by mutableStateOf(DrawerState())
        private set

    init {
        combine(
            profileInteractor.profile(),
            notificationInteractor.count(),
            cartInteractor.totalQuantity()
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
                        navigator.goTo(Destination.Login())
                    }
                }
            }
            DrawerEvent.Logout -> {
                state = state.copy(alert = null)
                viewModelScope.launch {
                    datastore.deleteAccessToken()
                    profileInteractor.deleteProfile()
                    favoriteInteractor.deleteFavorites()
                    orderInteractor.deleteOrders()
                    cartInteractor.clearCart()
                }
            }
            DrawerEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
        }
    }
}