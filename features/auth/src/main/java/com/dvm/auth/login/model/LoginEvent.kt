package com.dvm.auth.login.model

sealed class LoginEvent {
    object Login: LoginEvent()
    object RegisterClick: LoginEvent()
    object PasswordRestoreClick: LoginEvent()
    object DismissAlert: LoginEvent()
    data class LoginTextChanged(val login: String): LoginEvent()
    data class PasswordTextChanged(val password: String): LoginEvent()
    object BackClick: LoginEvent()
}