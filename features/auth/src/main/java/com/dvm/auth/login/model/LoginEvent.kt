package com.dvm.auth.login.model

sealed class LoginEvent {
    object Login: LoginEvent()
    object NavigateToRegister: LoginEvent()
    object NavigateToPasswordRestore: LoginEvent()
    object NavigateUp: LoginEvent()
    object DismissAlert: LoginEvent()
    data class LoginTextChanged(val login: String): LoginEvent()
    data class PasswordTextChanged(val password: String): LoginEvent()
}