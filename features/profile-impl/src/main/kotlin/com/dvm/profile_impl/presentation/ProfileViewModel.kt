package com.dvm.profile_impl.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.profile_api.domain.model.Profile
import com.dvm.profile_impl.presentation.model.ProfileEvent
import com.dvm.profile_impl.presentation.model.ProfileState
import com.dvm.utils.AppException
import com.dvm.utils.extensions.getEmailErrorOrNull
import com.dvm.utils.extensions.getTextFieldErrorOrNull
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class ProfileViewModel(
    private val interactor: ProfileInteractor,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        interactor
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Profile))
            }
            .launchIn(viewModelScope)

        interactor
            .profile()
            .filterNotNull()
            .onEach { profile ->
                state = state.copy(
                    firstName = profile.firstName,
                    lastName = profile.lastName,
                    email = profile.email
                )
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangeEmailText -> {
                state = state.copy(
                    email = event.email,
                    emailError = null
                )
            }
            is ProfileEvent.ChangeFirstName -> {
                state = state.copy(
                    firstName = event.firstName,
                    firstNameError = null
                )
            }
            is ProfileEvent.ChangeLastName -> {
                state = state.copy(
                    lastName = event.lastName,
                    lastNameError = null
                )
            }
            is ProfileEvent.ChangeEditingMode -> {
                state = state.copy(editing = event.editing)
            }
            ProfileEvent.EditPassword -> {
                state = state.copy(passwordChanging = true)
            }
            is ProfileEvent.ChangePassword -> {
                changePassword(
                    newPassword = event.newPassword,
                    oldPassword = event.oldPassword
                )
            }
            ProfileEvent.SaveProfile -> {
                saveProfile()
            }
            ProfileEvent.DismissPasswordDialog -> {
                state = state.copy(passwordChanging = false)
            }
            ProfileEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            ProfileEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun changePassword(newPassword: String, oldPassword: String) {
        state = state.copy(progress = true)
        viewModelScope.launch {
            try {
                interactor.changePassword(
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
                state = state.copy(
                    alert = CoreR.string.profile_message_password_changed,
                    progress = false,
                    passwordChanging = false
                )
            } catch (exception: CancellationException) {
                throw CancellationException()
            } catch (exception: Exception) {
                val message =
                    if (exception is AppException.BadRequest) {
                        CoreR.string.profile_message_wrong_password
                    } else {
                        exception.getErrorMessage()
                    }
                state = state.copy(
                    alert = message,
                    progress = false
                )
            }
        }
    }

    private fun saveProfile() {

        val firstNameError =
            state.firstName.getTextFieldErrorOrNull()
        val lastNameError =
            state.lastName.getTextFieldErrorOrNull()
        val emailError =
            state.email.getEmailErrorOrNull()

        if (
            firstNameError != null ||
            lastNameError != null ||
            emailError != null
        ) {
            state = state.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
            )
            return
        }

        state = state.copy(progress = true)

        viewModelScope.launch {
            try {
                interactor.updateProfileData(
                    Profile(
                        firstName = state.firstName,
                        lastName = state.lastName,
                        email = state.email
                    )
                )

                state = state.copy(
                    progress = false,
                    editing = false
                )
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