package com.dvm.utils.extensions

import android.content.Context
import androidx.core.util.PatternsCompat
import com.dvm.utils.R
import java.util.regex.Pattern

fun String.getTextFieldErrorOrNull(context: Context)= when {
    isEmpty() -> context.getString(emptyField)
    !isTextValid() -> context.getString(letters)
    else -> null
}

fun String.getEmailErrorOrNull(context: Context)= when {
    isEmpty() -> context.getString(emptyField)
    !isEmailValid() -> context.getString(incorrectEmail)
    else -> null
}

fun String.getPasswordErrorOrNull(context: Context)= when {
    isEmpty() -> context.getString(emptyField)
    !isPasswordValid() -> context.getString(incorrectPassword)
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