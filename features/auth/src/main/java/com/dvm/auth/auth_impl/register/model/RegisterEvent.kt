package com.dvm.auth.auth_impl.register.model

sealed class RegisterEvent {
    object NavigateUp: RegisterEvent()
    object Login: RegisterEvent()
    object Register: RegisterEvent()
    object DismissAlert: RegisterEvent()
    data class FirstNameTextChanged(val firstName: String): RegisterEvent()
    data class LastNameTextChanged(val lastName: String): RegisterEvent()
    data class EmailTextChanged(val email: String): RegisterEvent()
    data class PasswordTextChanged(val password: String): RegisterEvent()
}