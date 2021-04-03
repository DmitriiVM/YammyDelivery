package com.dvm.utils.di.extensions

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern


fun String.isEmailValid(): Boolean =
    this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPasswordValid(): Boolean {
    val passwordPattern = Pattern.compile("[a-zA-Z0-9]{6,24}")
    return passwordPattern.matcher(this).matches()
}

fun String.isTextValid(): Boolean {
    val passwordPattern = Pattern.compile("[a-zA-Zа-яА-Я]{1,24}")
    return passwordPattern.matcher(this).matches()
}