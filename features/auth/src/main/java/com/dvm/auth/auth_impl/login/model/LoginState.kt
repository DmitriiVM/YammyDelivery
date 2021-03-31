package com.dvm.auth.auth_impl.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val login: String = "",
    val password: String = ""
)