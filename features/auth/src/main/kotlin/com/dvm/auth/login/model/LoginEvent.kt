package com.dvm.auth.login.model

sealed class LoginEvent {

    data class Login(
        val email: String,
        val password: String
    ): LoginEvent()

    object Register: LoginEvent()
    object RestorePassword: LoginEvent()
    object DismissAlert: LoginEvent()
    object ChangeLogin: LoginEvent()
    object ChangePassword: LoginEvent()
    object Back: LoginEvent()
}