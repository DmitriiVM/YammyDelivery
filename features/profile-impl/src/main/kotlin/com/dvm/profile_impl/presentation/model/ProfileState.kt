package com.dvm.profile_impl.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
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