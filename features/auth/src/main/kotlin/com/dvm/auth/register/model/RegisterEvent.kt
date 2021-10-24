package com.dvm.auth.register.model

internal sealed class RegisterEvent {

    data class Register(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
    ) : RegisterEvent()

    object Login : RegisterEvent()
    object DismissAlert : RegisterEvent()
    object ChangeFirstName : RegisterEvent()
    object ChangeLastName : RegisterEvent()
    object ChangeEmail : RegisterEvent()
    object ChangePassword : RegisterEvent()
    object Back : RegisterEvent()
}