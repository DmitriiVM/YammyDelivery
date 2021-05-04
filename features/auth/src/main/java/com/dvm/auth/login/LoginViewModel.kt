package com.dvm.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.auth.R
import com.dvm.auth.login.model.LoginEvent
import com.dvm.auth.login.model.LoginState
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.network_api.api.AuthApi
import com.dvm.network.network_api.api.MenuApi
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import com.dvm.utils.extensions.isEmailValid
import com.dvm.utils.extensions.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val menuApi: MenuApi,
    private val favoriteRepository: FavoriteRepository,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun dispatch(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginTextChanged -> {
                state = state.copy(
                    email = event.login,
                    emailError = null
                )
            }
            is LoginEvent.PasswordTextChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = null
                )
            }
            LoginEvent.PasswordRestoreClick -> {
                navigator.goTo(Destination.PasswordRestore)
            }
            LoginEvent.RegisterClick -> {
                navigator.goTo(Destination.Register)
            }
            LoginEvent.BackClick -> {
                navigator.back()
            }
            LoginEvent.Login -> {
                login()
            }
            LoginEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
        }
    }

    private fun login() {
        val emptyField = stringProvider.getString(R.string.auth_field_error_empty)
        val incorrectEmail = stringProvider.getString(R.string.auth_field_error_incorrect_email)
        val incorrectPassword = stringProvider.getString(R.string.auth_field_error_incorrect_password)

        val emailError = when {
            state.email.isEmpty() -> emptyField
            !state.email.isEmailValid() -> incorrectEmail
            else -> null
        }
        val passwordError = when {
            state.password.isEmpty() -> emptyField
            !state.password.isPasswordValid() -> incorrectPassword
            else -> null
        }

        if (!emailError.isNullOrEmpty() || !passwordError.isNullOrEmpty()) {
            state = state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        state = state.copy(networkCall = true)

        viewModelScope.launch {
            try {
                val loginData = authApi.login(
                    login = state.email,
                    password = state.password
                )
                datastore.saveAccessToken(loginData.accessToken)
                datastore.saveRefreshToken(loginData.refreshToken)

                syncFavorites()

                navigator.goTo(Destination.LoginTarget)
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.message,
                    networkCall = false
                )
            }
        }
    }

    private suspend fun syncFavorites() {
        val remoteFavorites = menuApi.getFavorite().map { it.dishId }
        val localFavorites = favoriteRepository.getFavorites()
        val favoritesToLocal = remoteFavorites.filter { !localFavorites.contains(it) }
        val favoritesToRemote = localFavorites.filter { !remoteFavorites.contains(it) }

        favoriteRepository.addListToFavorite(favoritesToLocal)
        try {
            menuApi.changeFavorite(favoritesToRemote.associateWith { true })
        } catch (exception: Exception) {
            Log.e(TAG, "Can't change favorites on server: $exception")
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}