package com.dvm.auth.auth_impl.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.auth.auth_impl.register.model.RegisterEvent
import com.dvm.auth.auth_impl.register.model.RegisterState
import com.dvm.network.network_api.services.AuthService
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.extensions.isEmailValid
import com.dvm.utils.extensions.isPasswordValid
import com.dvm.utils.extensions.isTextValid
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RegisterViewModel(
    private val authService: AuthService,
    private val datastore: DatastoreRepository,
//    private val navController: NavController
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
        val emptyField = "Пустое поле"
        val letters = "Только буквы"
        val incorrectEmail = "Некорректный e-mail"
        val incorrectPassword = "Пароль должен состоять из 6 или более букв и цифр"

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
                val registerData = authService.register(
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

internal class RegisterViewModelFactory @Inject constructor(
    private val authService: AuthService,
    private val datastore: DatastoreRepository,
//    @Assisted private val navController: NavController
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                authService = authService,
                datastore = datastore,
//                navController = navController
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
//
//@AssistedFactory
//internal interface RegisterViewModelAssistedFactory {
//    fun create(navController: NavController): RegisterViewModelFactory
//}