package com.dvm.auth.register.model

sealed class RegisterEvent {
    object Login: RegisterEvent()
    object Register: RegisterEvent()
    object DismissAlert: RegisterEvent()
    data class FirstNameTextChanged(val firstName: String): RegisterEvent()
    data class LastNameTextChanged(val lastName: String): RegisterEvent()
    data class EmailTextChanged(val email: String): RegisterEvent()
    data class PasswordTextChanged(val password: String): RegisterEvent()
    object BackClick: RegisterEvent()
}