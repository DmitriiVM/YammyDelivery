package com.dvm.auth.restore.model

sealed class RestoreEvent {
    object NavigateUp: RestoreEvent()
    object SendLogin: RestoreEvent()
    object Save: RestoreEvent()
    data class LoginTextChanged(val login: String): RestoreEvent()
    data class PincodeTextChanged(val pincode: String): RestoreEvent()
    data class PasswordTextChanged(val password: String): RestoreEvent()
    data class ConfirmPasswordTextChanged(val confirmPassword: String): RestoreEvent()
}