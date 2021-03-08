package com.dvm.ui.themes

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

@Composable
fun YammyDeliveryTheme(
    window: Window,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    SideEffect {
        setSystemUiColors(window, darkTheme)
    }

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@Suppress("DEPRECATION")
private fun setSystemUiColors(window: Window, darkTheme: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val systemBackgroundColor = if (darkTheme) {
        Color.Black.copy(alpha = 0.5f).toArgb()
    } else {
        Color.White.copy(alpha = 0.5f).toArgb()
    }
    window.statusBarColor = systemBackgroundColor


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.navigationBarColor = systemBackgroundColor
        if (!darkTheme) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val wic = window.decorView.windowInsetsController
                wic?.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_STATUS_BARS,
                    APPEARANCE_LIGHT_STATUS_BARS
                )
                wic?.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_NAVIGATION_BARS,
                    APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
        }
    } else {
        if (darkTheme) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            if (Build.VERSION.SDK_INT >= 26){
                window.navigationBarColor = systemBackgroundColor
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (Build.VERSION.SDK_INT >= 26){
                window.navigationBarColor = systemBackgroundColor
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }
}