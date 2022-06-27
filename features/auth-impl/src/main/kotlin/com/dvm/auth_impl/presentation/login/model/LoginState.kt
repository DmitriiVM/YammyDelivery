package com.dvm.auth_impl.presentation.login.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class LoginState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val alert: Int? = null,
    val progress: Boolean = false
)