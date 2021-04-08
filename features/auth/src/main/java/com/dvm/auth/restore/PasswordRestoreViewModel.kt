package com.dvm.auth.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.network.network_api.api.AuthApi
import javax.inject.Inject

internal class PasswordRestoreViewModel(
    private val authApi: AuthApi,
//    private val navController: NavController
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
            RestoreEvent.NavigateUp -> {

            }
            RestoreEvent.Save -> {

            }
            RestoreEvent.SendLogin -> {

            }
        }
    }

//    private fun navigateUp() {
//        navController.navigateUp()
//    }
}

internal class PasswordRestoreViewModelFactory @Inject constructor(
    private val authApi: AuthApi,
//    @Assisted private val navController: NavController
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRestoreViewModel::class.java)) {
            return PasswordRestoreViewModel(authApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//
//@AssistedFactory
//internal interface PasswordRestoreViewModelAssistedFactory{
//    fun create(navController: NavController): PasswordRestoreViewModelFactory
//}