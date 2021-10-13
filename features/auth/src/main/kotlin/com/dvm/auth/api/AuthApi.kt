package com.dvm.auth.api

import androidx.compose.runtime.Composable
import com.dvm.auth.login.LoginScreen
import com.dvm.auth.register.RegisterScreen
import com.dvm.auth.restore.PasswordRestoreScreen

@Composable
fun Login() {
    LoginScreen()
}

@Composable
fun Registration() {
    RegisterScreen()
}

@Composable
fun PasswordRestoration() {
    PasswordRestoreScreen()
}