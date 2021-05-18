package com.dvm.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.FragmentActivity
import com.dvm.ui.themes.YammyDeliveryTheme
import com.dvm.utils.LocalBackPressedDispatcher

@Composable
fun YammyDeliveryScreen(
    activity: FragmentActivity,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBackPressedDispatcher provides activity.onBackPressedDispatcher
    ) {
        YammyDeliveryTheme(activity.window) {
            content()
        }
    }
}