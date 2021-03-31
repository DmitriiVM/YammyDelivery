package com.dvm.auth.auth_impl.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvm.auth.auth_impl.login.model.LoginEvent
import com.dvm.auth.auth_impl.login.model.LoginState
import javax.inject.Inject

internal class LoginViewModel : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun dispatch(event: LoginEvent){
        when (event) {
            is LoginEvent.LoginTextChanged -> state = state.copy(login = event.login)
            is LoginEvent.PasswordTextChanged -> state = state.copy(login = event.password)
            LoginEvent.Login -> login()
            LoginEvent.NavigateToPasswordRestore -> { }
            LoginEvent.NavigateToRegister -> { }
            LoginEvent.NavigateUp -> { }
        }
    }

    private fun login() {

    }

}

internal class LoginViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}