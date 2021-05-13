package com.dvm.profile.model

internal sealed class ProfileEvent {

    data class ChangePassword(
        val newPassword: String,
        val oldPassword: String
    ) : ProfileEvent()

    data class FirstNameTextChanged(val firstName: String): ProfileEvent()
    data class LastNameTextChanged(val lastName: String): ProfileEvent()
    data class ChangeEditingMode(val editing: Boolean) : ProfileEvent()
    data class EmailTextChanged(val email: String): ProfileEvent()
    object DismissPasswordDialog: ProfileEvent()
    object ChangeButtonClick : ProfileEvent()
    object DismissAlert: ProfileEvent()
    object SaveProfile: ProfileEvent()
    object BackClick: ProfileEvent()
}