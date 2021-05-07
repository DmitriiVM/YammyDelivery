package com.dvm.auth.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.auth.R
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.auth.restore.model.Screen
import com.dvm.navigation.Navigator
import com.dvm.network.api.api.AuthApi
import com.dvm.utils.StringProvider
import com.dvm.utils.extensions.getErrorMessage
import com.dvm.utils.extensions.hasCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PasswordRestoreViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val navigator: Navigator,
    private val stringProvider: StringProvider,
) : ViewModel() {

    var state by mutableStateOf(RestoreState())
        private set

    fun dispatch(event: RestoreEvent) {
        when (event) {
            is RestoreEvent.SendEmail -> {
                sendEmail(event.email)
            }
            is RestoreEvent.VerifyCode -> {
                verifyCode(
                    email = event.email,
                    code = event.code
                )
            }
            is RestoreEvent.ResetPassword -> {
                resetPassword(
                    email = event.email,
                    code = event.code,
                    password = event.password
                )
            }
            RestoreEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            RestoreEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun sendEmail(email: String) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                authApi.sendEmail(email)
                state = state.copy(
                    networkCall = false,
                    screen = Screen.CODE
                )
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = if (exception.hasCode(400)) {
                        stringProvider.getString(R.string.password_restoration_message_already_sent)
                    } else {
                        exception.getErrorMessage(stringProvider)
                    }
                )
            }
        }
    }

    private fun verifyCode(email: String, code: String) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                authApi.sendCode(email, code)
                state = state.copy(
                    networkCall = false,
                    screen = Screen.PASSWORD
                )
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = if (exception.hasCode(400)) {
                        stringProvider.getString(R.string.password_restoration_message_wrong_code)
                    } else {
                        exception.message
                    }
                )
            }
        }
    }

    private fun resetPassword(
        email: String,
        code: String,
        password: String
    ) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                authApi.resetPassword(
                    email = email,
                    code = code,
                    password = password
                )
                navigator.back()
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = if (exception.hasCode(402)) {
                        stringProvider.getString(R.string.password_restoration_message_expired_code)
                    } else {
                        exception.message
                    }
                )
            }
        }
    }
}
