package com.dvm.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.auth.R
import com.dvm.auth.register.model.RegisterEvent
import com.dvm.auth.register.model.RegisterState
import com.dvm.network.network_api.api.AuthApi
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import com.dvm.utils.extensions.isEmailValid
import com.dvm.utils.extensions.isPasswordValid
import com.dvm.utils.extensions.isTextValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RegisterViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun dispatch(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FirstNameTextChanged -> {
                state = state.copy(
                    firstName = event.firstName,
                    firstNameError = null
                )
            }
            is RegisterEvent.LastNameTextChanged -> {
                state = state.copy(
                    lastName = event.lastName,
                    lastNameError = null
                )
            }
            is RegisterEvent.EmailTextChanged -> {
                state = state.copy(
                    email = event.email,
                    emailError = null
                )
            }
            is RegisterEvent.PasswordTextChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = null
                )
            }
            RegisterEvent.Register -> {
                register()
            }
            RegisterEvent.Login -> {
//                navController.navigate(
//                    RegisterFragmentDirections.toLoginFragment()
//                )
            }
            RegisterEvent.NavigateUp -> {
//                navController.navigateUp()
            }
            RegisterEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
        }
    }

    private fun register() {
        val emptyField = stringProvider.getString(R.string.empty_edit_field)
        val letters = stringProvider.getString(R.string.only_letters)
        val incorrectEmail = stringProvider.getString(R.string.IncorrectEmail)
        val incorrectPassword = stringProvider.getString(R.string.incorrect_password)

        val firstNameError = when {
            state.firstName.isEmpty() -> emptyField
            !state.firstName.isTextValid() -> letters
            else -> null
        }
        val lastNameError = when {
            state.lastName.isEmpty() -> emptyField
            !state.lastName.isTextValid() -> letters
            else -> null
        }
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

        if (
            !firstNameError.isNullOrEmpty() ||
            !lastNameError.isNullOrEmpty() ||
            !emailError.isNullOrEmpty() ||
            !passwordError.isNullOrEmpty()
        ) {
            state = state.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        state = state.copy(networkCall = true)

        viewModelScope.launch {
            try {
                val registerData = authApi.register(
                    firstName = state.firstName,
                    lastName = state.lastName,
                    email = state.email,
                    password = state.password
                )
                datastore.saveAccessToken(registerData.accessToken)
                datastore.saveRefreshToken(registerData.refreshToken)

//                navController.popBackStack()
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }
}