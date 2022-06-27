package com.dvm.yammydelivery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dvm.auth_api.AuthNavHost
import com.dvm.cart_api.CartNavHost
import com.dvm.menu_api.MenuNavHost
import com.dvm.notifications_api.NotificationNavHost
import com.dvm.order_api.OrderNavHost
import com.dvm.profile_api.ProfileNavHost
import com.dvm.splash_api.SplashNavHost
import org.koin.androidx.compose.get

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    val profileNavHost = get<ProfileNavHost>()
    val orderNavHost = get<OrderNavHost>()
    val authNavHost = get<AuthNavHost>()
    val cartNavHost = get<CartNavHost>()
    val menuNavHost = get<MenuNavHost>()
    val notificationNavHost = get<NotificationNavHost>()
    val splashNavHost = get<SplashNavHost>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        profileNavHost.addComposables(this)
        orderNavHost.addComposables(this, navController)
        authNavHost.addComposables(this)
        cartNavHost.addComposables(this)
        menuNavHost.addComposables(this)
        notificationNavHost.addComposables(this)
        splashNavHost.addComposables(this)
    }
}

