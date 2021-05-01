package com.dvm.auth.restore.model

sealed class RestoreEvent {
    data class SendEmail(val email: String) : RestoreEvent()
    data class VerifyCode(
        val email: String,
        val code: String
    ) : RestoreEvent()
    class ResetPassword(
        val email: String,
        val code: String,
        val password: String
    ) : RestoreEvent()
    object DismissAlert : RestoreEvent()
    object BackClick : RestoreEvent()
}