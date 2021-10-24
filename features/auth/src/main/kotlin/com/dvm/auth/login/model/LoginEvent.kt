package com.dvm.auth.login.model

internal sealed class LoginEvent {
    object Register : LoginEvent()
    object RestorePassword : LoginEvent()
    object DismissAlert : LoginEvent()
    object ChangeLogin : LoginEvent()
    object ChangePassword : LoginEvent()
    object Back : LoginEvent()
}