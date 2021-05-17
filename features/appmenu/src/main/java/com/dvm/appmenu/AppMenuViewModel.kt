package com.dvm.appmenu

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.appmenu.model.AppMenuEvent
import com.dvm.appmenu.model.AppMenuState
import com.dvm.db.api.*
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AppMenuViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AppMenuState())
        private set

    private lateinit var hiltEntryPoint: AppViewModelEntryPoint

    private val datastore get() = hiltEntryPoint.datastore()
    private val profileRepository get() = hiltEntryPoint.profileRepository()
    private val favoriteRepository get() = hiltEntryPoint.favoriteRepository()
    private val orderRepository get() = hiltEntryPoint.orderRepository()
    private val cartRepository get() = hiltEntryPoint.cartRepository()
    private val notificationRepository get() = hiltEntryPoint.notificationRepository()
    private val navigator get() = hiltEntryPoint.navigator()
    private val appContext get() = hiltEntryPoint.context()

    fun init(context: Context) {
        hiltEntryPoint =
            EntryPointAccessors.fromApplication(context, AppViewModelEntryPoint::class.java)

        combine(
            profileRepository
                .profile(),
            notificationRepository
                .count(),
            cartRepository
                .totalQuantity()
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

    fun onEvent(event: AppMenuEvent) {
        when (event) {
            is AppMenuEvent.ItemClick -> {
                navigator.goTo(
                    when (event.drawerItem) {
                        DrawerItem.MAIN -> Destination.Main
                        DrawerItem.MENU -> Destination.Menu
                        DrawerItem.FAVORITE -> Destination.Favorite
                        DrawerItem.CART -> Destination.Cart
                        DrawerItem.PROFILE -> Destination.Profile
                        DrawerItem.ORDERS -> Destination.Orders
                        DrawerItem.NOTIFICATION -> Destination.Notification
                    }
                )
            }
            AppMenuEvent.AuthClick -> {
                viewModelScope.launch {
                    if (datastore.isAuthorized()) {
                        state = state.copy(
                            alertMessage = appContext.getString(R.string.app_menu_message_logout)
                        )
                    } else {
                        hiltEntryPoint.navigator().goTo(Destination.Login)
                    }
                }
            }
            AppMenuEvent.LogoutClick -> {
                viewModelScope.launch {
                    datastore.deleteAccessToken()
                    profileRepository.deleteProfile()
                    favoriteRepository.deleteFavorites()
                    orderRepository.deleteOrders()
                    cartRepository.clearCart()
                }
            }
            AppMenuEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
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
        @ApplicationContext
        fun context(): Context
    }
}