package com.dvm.yammydelivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.dvm.notifications.NotificationService.Companion.NOTIFICATION_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.main_activity)
    }

    override fun onStart() {
        super.onStart()
        viewModel.navController = findNavController(R.id.fragmentContainerView)
        handleNotificationIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.navController = null
    }

    private fun handleNotificationIntent(intent: Intent?) {
        if (intent?.hasExtra(NOTIFICATION_EXTRA) == true) {
            viewModel.navigateToNotification()
            intent.removeExtra(NOTIFICATION_EXTRA)
        }
    }
}