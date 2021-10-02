package com.dvm.auth.login.model

data class LoginState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val alert: Int? = null,
    val progress: Boolean = false
)