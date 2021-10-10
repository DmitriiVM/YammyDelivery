package com.dvm.yammydelivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.dvm.navigation.api.model.Destination
import com.dvm.notification.NotificationService.Companion.NOTIFICATION_EXTRA
import com.dvm.ui.YammyDeliveryScreen
import com.google.accompanist.insets.ProvideWindowInsets

internal class MainActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            YammyDeliveryScreen(this) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    val navController = rememberNavController()
                    viewModel.navController = navController
                    NavHost(
                        navController = navController,
                        startDestination = if (fromNotification(intent)) {
                            Destination.Main.route
                        } else {
                            Destination.Splash.route
                        }
                    )
                    if (fromNotification(intent)) {
                        handleNotificationIntent(intent)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (fromNotification(intent)) {
            handleNotificationIntent(intent)
        }
    }

    private fun handleNotificationIntent(intent: Intent?) {
        viewModel.navigateToNotification()
        intent?.removeExtra(NOTIFICATION_EXTRA)
    }

    private fun fromNotification(intent: Intent?): Boolean =
        intent?.hasExtra(NOTIFICATION_EXTRA) == true
}