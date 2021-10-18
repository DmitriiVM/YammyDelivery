package com.dvm.auth.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.auth.restore.model.Screen
import com.dvm.navigation.api.Navigator
import com.dvm.network.api.AuthApi
import com.dvm.utils.AppException
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class PasswordRestoreViewModel(
    private val authApi: AuthApi,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(RestoreState())
        private set

    private val screen = savedState.getLiveData("password_restoration_screen", Screen.EMAIL)

    init {
        screen.asFlow()
            .distinctUntilChanged()
            .onEach { state = state.copy(screen = it) }
            .launchIn(viewModelScope)
    }

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
                state = state.copy(alert = null)
            }
            RestoreEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun sendEmail(email: String) {
        state = state.copy(progress = true)
        viewModelScope.launch {
            try {
                authApi.sendEmail(email)
                state = state.copy(progress = false)
                screen.value = Screen.CODE
            } catch (exception: Exception) {
                state = state.copy(
                    progress = false,
                    alert = if (exception is AppException.BadRequest) {
                        CoreR.string.password_restoration_message_already_sent
                    } else {
                        exception.getErrorMessage()
                    }
                )
            }
        }
    }

    private fun verifyCode(email: String, code: String) {
        state = state.copy(progress = true)
        viewModelScope.launch {
            try {
                authApi.sendCode(email, code)
                state = state.copy(progress = false)
                screen.value = Screen.PASSWORD
            } catch (exception: Exception) {
                state = state.copy(
                    progress = false,
                    alert = if (exception is AppException.BadRequest) {
                        CoreR.string.password_restoration_message_wrong_code
                    } else {
                        exception.getErrorMessage()
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
        state = state.copy(progress = true)
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
                    progress = false,
                    alert = if (exception is AppException.IncorrectData) {
                        CoreR.string.password_restoration_message_expired_code
                    } else {
                        exception.getErrorMessage()
                    }
                )
            }
        }
    }
}
