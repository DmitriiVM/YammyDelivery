package com.dvm.auth.register.model

sealed class RegisterEvent {

    data class Register(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
    ): RegisterEvent()

    object Login: RegisterEvent()
    object DismissAlert: RegisterEvent()
    object FirstNameTextChanged: RegisterEvent()
    object LastNameTextChanged: RegisterEvent()
    object EmailTextChanged: RegisterEvent()
    object PasswordTextChanged: RegisterEvent()
    object BackClick: RegisterEvent()
}