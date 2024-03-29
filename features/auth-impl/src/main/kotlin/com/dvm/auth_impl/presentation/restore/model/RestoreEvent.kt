package com.dvm.auth_impl.presentation.restore.model

internal sealed class RestoreEvent {

    data class VerifyCode(
        val email: String,
        val code: String
    ) : RestoreEvent()

    class ResetPassword(
        val email: String,
        val code: String,
        val password: String
    ) : RestoreEvent()

    data class SendEmail(val email: String) : RestoreEvent()
    object DismissAlert : RestoreEvent()
    object Back : RestoreEvent()
}