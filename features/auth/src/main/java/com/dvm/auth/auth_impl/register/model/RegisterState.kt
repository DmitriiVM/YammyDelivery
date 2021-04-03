package com.dvm.auth.auth_impl.register.model

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val alertMessage: String? = null,
    val networkCall: Boolean = false
)