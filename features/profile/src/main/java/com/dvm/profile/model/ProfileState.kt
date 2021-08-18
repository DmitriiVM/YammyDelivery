package com.dvm.profile.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val editing: Boolean = false,
    val alert: String? = null,
    val progress: Boolean = false,
    val passwordChanging: Boolean = false
)