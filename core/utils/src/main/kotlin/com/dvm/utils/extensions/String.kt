package com.dvm.utils.extensions

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern
import com.dvm.ui.R as CoreR

fun String.getTextFieldErrorOrNull()= when {
    isEmpty() -> emptyField
    !isTextValid() -> letters
    else -> null
}

fun String.getEmailErrorOrNull()= when {
    isEmpty() -> emptyField
    !isEmailValid() -> incorrectEmail
    else -> null
}

fun String.getPasswordErrorOrNull()= when {
    isEmpty() -> emptyField
    !isPasswordValid() -> incorrectPassword
    else -> null
}

private fun String.isTextValid(): Boolean {
    val passwordPattern = Pattern.compile("[a-zA-Zа-яА-Я]{1,24}")
    return passwordPattern.matcher(this).matches()
}

private fun String.isEmailValid(): Boolean =
    this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

private fun String.isPasswordValid(): Boolean {
    val passwordPattern = Pattern.compile("[a-zA-Z0-9]{6,24}")
    return passwordPattern.matcher(this).matches()
}

private val emptyField = CoreR.string.auth_field_error_empty
private val letters = CoreR.string.auth_field_error_letters_allowed
private val incorrectEmail = CoreR.string.auth_field_error_incorrect_email
private val incorrectPassword = CoreR.string.auth_field_error_incorrect_password