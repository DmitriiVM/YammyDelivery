package com.dvm.auth.restore.model

import androidx.compose.runtime.Immutable

@Immutable
data class RestoreState(
    val screen: Screen = Screen.EMAIL,
    val alert: Int? = null,
    val progress: Boolean = false
)

enum class Screen{
    EMAIL,
    CODE,
    PASSWORD
}