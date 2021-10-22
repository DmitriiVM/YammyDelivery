package com.dvm.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink

@Composable
fun <T> NavHostController.BackStackValueObserver(
    key: String,
    onReceive: (T) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    currentBackStackEntry?.let { entry ->
        entry.savedStateHandle
            .getLiveData<T>(key)
            .observe(lifecycle) { onReceive(it) }
    }
}

val navDeepLinks = emptyList<NavDeepLink>()

fun List<NavDeepLink>.addUri(uri: String): List<NavDeepLink> =
    this + navDeepLink { uriPattern = uri }