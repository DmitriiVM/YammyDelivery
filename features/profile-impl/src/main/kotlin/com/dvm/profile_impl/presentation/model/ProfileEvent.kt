package com.dvm.profile_impl.presentation.model

internal sealed class ProfileEvent {

    data class ChangePassword(
        val newPassword: String,
        val oldPassword: String
    ) : ProfileEvent()

    data class ChangeFirstName(val firstName: String) : ProfileEvent()
    data class ChangeLastName(val lastName: String) : ProfileEvent()
    data class ChangeEditingMode(val editing: Boolean) : ProfileEvent()
    data class ChangeEmailText(val email: String) : ProfileEvent()
    object DismissPasswordDialog : ProfileEvent()
    object EditPassword : ProfileEvent()
    object DismissAlert : ProfileEvent()
    object SaveProfile : ProfileEvent()
    object Back : ProfileEvent()
}