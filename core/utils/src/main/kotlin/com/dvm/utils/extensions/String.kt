package com.dvm.utils.extensions

import androidx.core.util.PatternsCompat
import com.dvm.utils.R
import java.util.regex.Pattern

fun String.getTextFieldErrorOrNull(): Int? = when {
    isEmpty() -> emptyField
    !isTextValid() -> letters
    else -> null
}

fun String.getEmailErrorOrNull(): Int? = when {
    isEmpty() -> emptyField
    !isEmailValid() -> incorrectEmail
    else -> null
}

fun String.getPasswordErrorOrNull(): Int? = when {
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

private val emptyField = R.string.auth_field_error_empty
private val letters = R.string.auth_field_error_letters_allowed
private val incorrectEmail = R.string.auth_field_error_incorrect_email
private val incorrectPassword = R.string.auth_field_error_incorrect_password