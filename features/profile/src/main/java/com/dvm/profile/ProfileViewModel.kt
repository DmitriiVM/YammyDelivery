package com.dvm.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.ProfileRepository
import com.dvm.db.api.models.Profile
import com.dvm.navigation.Navigator
import com.dvm.network.api.ProfileApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile.model.ProfileEvent
import com.dvm.profile.model.ProfileState
import com.dvm.utils.StringProvider
import com.dvm.utils.extensions.isEmailValid
import com.dvm.utils.extensions.isTextValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileApi: ProfileApi,
    private val profileRepository: ProfileRepository,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
//        viewModelScope.launch {
//            if (!datastore.isAuthorized()){
//                navigator.goTo(Destination.Auth)
//            }
//        }

        profileRepository
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

    fun dispatchEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EmailTextChanged -> {
                state = state.copy(
                    email = event.email,
                    emailError = null
                )
            }
            is ProfileEvent.FirstNameTextChanged -> {
                state = state.copy(
                    firstName = event.firstName,
                    firstNameError = null
                )
            }
            is ProfileEvent.LastNameTextChanged -> {
                state = state.copy(
                    lastName = event.lastName,
                    lastNameError = null
                )
            }
            is ProfileEvent.ChangeIsEditing -> {
                state = state.copy(isEditing = event.editing)
            }
            ProfileEvent.ChangePasswordClick -> {
                state = state.copy(passwordChanging = true)
            }
            ProfileEvent.SaveProfile -> {
                saveProfile()
            }
            ProfileEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            ProfileEvent.BackClick -> {
                navigator.back()
            }
            is ProfileEvent.ChangePassword -> {
                changePassword(
                    newPassword = event.newPassword,
                    oldPassword = event.oldPassword
                )
            }
            ProfileEvent.DismissPasswordDialog -> {
                state = state.copy(passwordChanging = false)
            }
        }
    }

    private fun changePassword(newPassword: String, oldPassword: String) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                profileApi.changePassword(
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
                state = state.copy(
                    networkCall = false,
                    passwordChanging = false
                )
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }

    private fun saveProfile() {
        val emptyField = stringProvider.getString(R.string.auth_field_error_empty)
        val letters = stringProvider.getString(R.string.auth_field_error_letters_allowed)
        val incorrectEmail = stringProvider.getString(R.string.auth_field_error_incorrect_email)

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

        if (
            !firstNameError.isNullOrEmpty() ||
            !lastNameError.isNullOrEmpty() ||
            !emailError.isNullOrEmpty()
        ) {
            state = state.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
            )
            return
        }

        state = state.copy(networkCall = true)

        viewModelScope.launch {
            try {
                val profile = profileApi.editProfile(
                    firstName = state.firstName,
                    lastName = state.lastName,
                    email = state.email
                )

                profileRepository.updateProfile(
                    Profile(
                        firstName = profile.firstName,
                        lastName = profile.lastName,
                        email = profile.email
                    )
                )

                state = state.copy(
                    networkCall = false,
                    isEditing = false
                )
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }
}