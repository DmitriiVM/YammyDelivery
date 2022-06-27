package com.dvm.auth_impl.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.auth_impl.domain.AuthInteractor
import com.dvm.auth_impl.presentation.register.model.RegisterEvent
import com.dvm.auth_impl.presentation.register.model.RegisterState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.updateservice.api.UpdateService
import com.dvm.utils.extensions.getEmailErrorOrNull
import com.dvm.utils.extensions.getPasswordErrorOrNull
import com.dvm.utils.extensions.getTextFieldErrorOrNull
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val authInteractor: AuthInteractor,
    private val datastore: DatastoreRepository,
    private val profileInteractor: ProfileInteractor,
    private val updateService: UpdateService,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val firstNameError = savedState.getLiveData<Int>("register_name_error")
    private val lastNameError = savedState.getLiveData<Int>("register_last_name_error")
    private val emailError = savedState.getLiveData<Int>("register_email_error")
    private val passwordError = savedState.getLiveData<Int>("register_password_error")

    init {
        combine(
            firstNameError.asFlow()
                .distinctUntilChanged(),
            lastNameError.asFlow()
                .distinctUntilChanged(),
            emailError.asFlow()
                .distinctUntilChanged(),
            passwordError.asFlow()
                .distinctUntilChanged(),
        ) { firstNameError, lastNameError, emailError, passwordError ->
            state = state.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
                passwordError = passwordError,
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.ChangeFirstName -> {
                firstNameError.value = null
            }
            is RegisterEvent.ChangeLastName -> {
                lastNameError.value = null
            }
            is RegisterEvent.ChangeEmail -> {
                emailError.value = null
            }
            is RegisterEvent.ChangePassword -> {
                passwordError.value = null
            }
            is RegisterEvent.Register -> {
                register(
                    firstName = event.firstName,
                    lastName = event.lastName,
                    email = event.email,
                    password = event.password,
                )
            }
            RegisterEvent.Login -> {
                navigator.goTo(Destination.Login())
            }
            RegisterEvent.Back -> {
                navigator.back()
            }
            RegisterEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
        }
    }

    private fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {

        val firstNameError = firstName.getTextFieldErrorOrNull()
        val lastNameError = lastName.getTextFieldErrorOrNull()
        val emailError = email.getEmailErrorOrNull()
        val passwordError = password.getPasswordErrorOrNull()

        if (
            firstNameError != null ||
            lastNameError != null ||
            emailError != null ||
            passwordError != null
        ) {
            this.firstNameError.value = firstNameError
            this.lastNameError.value = lastNameError
            this.emailError.value = emailError
            this.passwordError.value = passwordError
            return
        }

        state = state.copy(progress = true)

        viewModelScope.launch {
            try {
                authInteractor.register(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password
                )
                navigator.goTo(Destination.FinishRegister)
            } catch (exception: CancellationException) {
                throw CancellationException()
            } catch (exception: Exception) {
                state = state.copy(
                    alert = exception.getErrorMessage(),
                    progress = false
                )
            }
        }
    }
}