package com.dvm.profile.model

internal sealed class ProfileEvent {
    data class FirstNameTextChanged(val firstName: String): ProfileEvent()
    data class LastNameTextChanged(val lastName: String): ProfileEvent()
    data class EmailTextChanged(val email: String): ProfileEvent()
    data class ChangeIsEditing(val editing: Boolean) : ProfileEvent()
    data class ChangePassword(
        val newPassword: String,
        val oldPassword: String
    ) : ProfileEvent()
    object DismissPasswordDialog: ProfileEvent()
    object DismissAlert: ProfileEvent()
    object ChangePasswordClick : ProfileEvent()
    object SaveProfile: ProfileEvent()
    object BackClick: ProfileEvent()
}