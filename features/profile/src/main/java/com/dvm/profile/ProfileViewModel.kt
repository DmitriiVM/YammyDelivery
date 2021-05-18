package com.dvm.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.ProfileRepository
import com.dvm.db.api.models.Profile
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.ProfileApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile.model.ProfileEvent
import com.dvm.profile.model.ProfileState
import com.dvm.utils.extensions.getEmailErrorOrNull
import com.dvm.utils.extensions.getTextFieldErrorOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileApi: ProfileApi,
    private val profileRepository: ProfileRepository,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Profile))
            }
            .launchIn(viewModelScope)

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
            is ProfileEvent.ChangeEditingMode -> {
                state = state.copy(isEditing = event.editing)
            }
            ProfileEvent.ChangeButtonClick -> {
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
                state = state.copy(alertMessage = null)
            }
            ProfileEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun changePassword(newPassword: String, oldPassword: String) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                profileApi.changePassword(
                    token = requireNotNull(datastore.getAccessToken()),
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

        val firstNameError =
            state.firstName.getTextFieldErrorOrNull(context)
        val lastNameError =
            state.lastName.getTextFieldErrorOrNull(context)
        val emailError =
            state.email.getEmailErrorOrNull(context)

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
                    token = requireNotNull(datastore.getAccessToken()),
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