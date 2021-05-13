package com.dvm.auth.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val alertMessage: String? = null,
    val networkCall: Boolean = false
)