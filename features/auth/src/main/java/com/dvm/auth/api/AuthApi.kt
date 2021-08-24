package com.dvm.auth.api

import androidx.compose.runtime.Composable
import com.dvm.auth.login.Login
import com.dvm.auth.register.Registration
import com.dvm.auth.restore.PasswordRestoration

@Composable
fun LoginScreen() { Login() }

@Composable
fun RegistrationScreen() { Registration() }

@Composable
fun PasswordRestoreScreen() { PasswordRestoration() }