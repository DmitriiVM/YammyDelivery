package com.dvm.auth.register.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class RegisterState(
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val alert: Int? = null,
    val progress: Boolean = false
)