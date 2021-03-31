package com.dvm.auth.auth_impl.login.model

sealed class LoginEvent {
    object Login: LoginEvent()
    object NavigateToRegister: LoginEvent()
    object NavigateToPasswordRestore: LoginEvent()
    object NavigateUp: LoginEvent()
    data class LoginTextChanged(val login: String): LoginEvent()
    data class PasswordTextChanged(val password: String): LoginEvent()
}