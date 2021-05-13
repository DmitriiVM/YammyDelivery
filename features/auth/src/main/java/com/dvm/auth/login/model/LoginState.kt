package com.dvm.auth.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
//    val email: String = "",
    val emailError: String? = null,
//    val password: String = "",
    val passwordError: String? = null,
    val alertMessage: String? = null,
    val networkCall: Boolean = false
)