package com.dvm.auth.auth_impl.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dvm.auth.auth_impl.login.model.LoginEvent
import com.dvm.auth.auth_impl.login.model.LoginState
import com.dvm.network.network_api.services.AuthService
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.di.extensions.isEmailValid
import com.dvm.utils.di.extensions.isPasswordValid
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authService: AuthService,
    private val datastore: DatastoreRepository,
    private val navController: NavController
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
            LoginEvent.NavigateToPasswordRestore -> {
                navController.navigate(
                    LoginFragmentDirections.toPasswordRestoreFragment()
                )
            }
            LoginEvent.NavigateToRegister -> {
                navController.navigate(
                    LoginFragmentDirections.toRegisterFragment()
                )
            }
            LoginEvent.NavigateUp -> {
                navController.navigateUp()
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
        val emptyField = "Пустое поле"
        val incorrectEmail = "Некорректный e-mail"
        val incorrectPassword = "Пароль должен состоять из 6 или более букв и цифр"

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
                val loginData = authService.login(
                    login = state.email,
                    password = state.password
                )
                datastore.saveAccessToken(loginData.accessToken)
                datastore.saveRefreshToken(loginData.refreshToken)
                Log.d("mmm", "LoginViewModel :  login --  $loginData")

                navController.popBackStack()
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }
}

internal class LoginViewModelFactory @AssistedInject constructor(
    private val authService: AuthService,
    private val datastore: DatastoreRepository,
    @Assisted private val navController: NavController
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                authService = authService,
                datastore = datastore,
                navController = navController
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface LoginViewModelAssistedFactory{
    fun create(navController: NavController): LoginViewModelFactory
}