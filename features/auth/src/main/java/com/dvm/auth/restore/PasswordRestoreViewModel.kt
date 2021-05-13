package com.dvm.auth.restore

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.auth.R
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.auth.restore.model.Screen
import com.dvm.navigation.Navigator
import com.dvm.network.api.AuthApi
import com.dvm.utils.getErrorMessage
import com.dvm.utils.hasCode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class PasswordRestoreViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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
                state = state.copy(networkCall = false)
                screen.value = Screen.CODE
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = if (exception.hasCode(400)) {
                        context.getString(R.string.password_restoration_message_already_sent)
                    } else {
                        exception.getErrorMessage(context)
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
                state = state.copy(networkCall = false)
                screen.value = Screen.PASSWORD
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = if (exception.hasCode(400)) {
                        context.getString(R.string.password_restoration_message_wrong_code)
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
                        context.getString(R.string.password_restoration_message_expired_code)
                    } else {
                        exception.message
                    }
                )
            }
        }
    }
}
