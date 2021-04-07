package com.dvm.auth.restore.model

import androidx.compose.runtime.Immutable

@Immutable
data class RestoreState(
    val login: String ="",
    val pincode: String ="",
    val password: String ="",
    val confirmPassword: String ="",
)