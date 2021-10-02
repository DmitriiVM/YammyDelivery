package com.dvm.profile.model

internal data class ProfileState(
    val firstName: String = "",
    val firstNameError: Int? = null,
    val lastName: String = "",
    val lastNameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val editing: Boolean = false,
    val alert: Int? = null,
    val progress: Boolean = false,
    val passwordChanging: Boolean = false
)