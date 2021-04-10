package com.dvm.auth.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.network.network_api.api.AuthApi

internal class PasswordRestoreViewModel(
    private val authApi: AuthApi,
): ViewModel() {

    var state by mutableStateOf(RestoreState())
        private set

    fun dispatch(event: RestoreEvent) {
        when (event) {
            is RestoreEvent.ConfirmPasswordTextChanged -> {

            }
            is RestoreEvent.LoginTextChanged -> {

            }
            is RestoreEvent.PasswordTextChanged -> {

            }
            is RestoreEvent.PincodeTextChanged -> {

            }
            RestoreEvent.BackClick -> {

            }
            RestoreEvent.Save -> {

            }
            RestoreEvent.SendLogin -> {

            }
        }
    }
}