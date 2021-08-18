package com.dvm.auth.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val alert: String? = null,
    val progress: Boolean = false
)