package com.dvm.auth.register.model

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterState(
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val alertMessage: String? = null,
    val networkCall: Boolean = false
)