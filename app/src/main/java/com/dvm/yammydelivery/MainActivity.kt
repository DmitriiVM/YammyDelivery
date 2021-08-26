package com.dvm.yammydelivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.dvm.notification.NotificationService.Companion.NOTIFICATION_EXTRA
import com.dvm.ui.YammyDeliveryScreen
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.setNavHostController(this)
        setContent {
            YammyDeliveryScreen(this) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true){
                    NavHost(viewModel.navController!!)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        handleNotificationIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        if (intent?.hasExtra(NOTIFICATION_EXTRA) == true) {
            viewModel.navigateToNotification()
            intent.removeExtra(NOTIFICATION_EXTRA)
        }
    }
}