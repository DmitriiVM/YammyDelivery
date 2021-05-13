package com.dvm.auth.login.model

sealed class LoginEvent {

    data class Login(
        val email: String,
        val password: String
    ): LoginEvent()

    object RegisterClick: LoginEvent()
    object PasswordRestoreClick: LoginEvent()
    object DismissAlert: LoginEvent()
    object LoginTextChanged: LoginEvent()
    object PasswordTextChanged: LoginEvent()
    object BackClick: LoginEvent()
}