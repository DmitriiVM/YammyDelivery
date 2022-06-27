package com.dvm.notifications_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvm.navigation.api.model.Destination
import com.dvm.notifications_api.NOTIFICATION_URI
import com.dvm.notifications_api.NotificationNavHost
import com.dvm.notifications_impl.presentation.NotificationScreen
import com.dvm.utils.addUri
import com.dvm.utils.navDeepLinks

internal class DefaultNotificationNavHost : NotificationNavHost {

    override fun addComposables(navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.composable(
            route = Destination.Notification.route,
            deepLinks = navDeepLinks.addUri(NOTIFICATION_URI)
        ) {
            NotificationScreen()
        }
    }
}