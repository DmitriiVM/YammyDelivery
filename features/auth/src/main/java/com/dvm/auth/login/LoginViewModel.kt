package com.dvm.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.auth.R
import com.dvm.auth.login.model.LoginEvent
import com.dvm.auth.login.model.LoginState
import com.dvm.navigation.Destination
import com.dvm.navigation.Navigator
import com.dvm.network.network_api.api.AuthApi
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import com.dvm.utils.extensions.isEmailValid
import com.dvm.utils.extensions.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun dispatch(event: LoginEvent){
        when (event) {
            is LoginEvent.LoginTextChanged -> {
                state = state.copy(
                    email = event.login,
                    emailError = null
                )
            }
            is LoginEvent.PasswordTextChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = null
                )
            }
            LoginEvent.PasswordRestoreClick -> {
                navigator.navigationTo?.invoke(Destination.PasswordRestore)
            }
            LoginEvent.RegisterClick -> {
                navigator.navigationTo?.invoke(Destination.Register)
            }
            LoginEvent.BackClick -> {
                navigator.navigationTo?.invoke(Destination.Back)
            }
            LoginEvent.Login -> {
                login()
            }
            LoginEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
        }
    }

    private fun login() {
        val emptyField = stringProvider.getString(R.string.auth_field_error_empty)
        val incorrectEmail = stringProvider.getString(R.string.auth_field_error_incorrect_email)
        val incorrectPassword = stringProvider.getString(R.string.auth_field_error_incorrect_password)

        val emailError = when {
            state.email.isEmpty() -> emptyField
            !state.email.isEmailValid() -> incorrectEmail
            else -> null
        }
        val passwordError = when {
            state.password.isEmpty() -> emptyField
            !state.password.isPasswordValid() -> incorrectPassword
            else -> null
        }

        if (!emailError.isNullOrEmpty() || !passwordError.isNullOrEmpty()) {
            state = state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        state = state.copy(networkCall = true)

        viewModelScope.launch {
            try {
                val loginData = authApi.login(
                    login = state.email,
                    password = state.password
                )
                datastore.saveAccessToken(loginData.accessToken)
                datastore.saveRefreshToken(loginData.refreshToken)
                Log.d("mmm", "LoginViewModel :  login --  $loginData")


                navigator.navigationTo?.invoke(Destination.Back)
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }
}