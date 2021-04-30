package com.dvm.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.auth.login.model.LoginEvent
import com.dvm.auth.login.model.LoginState
import com.dvm.navigation.Navigator
import com.dvm.ui.components.*
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Login(
    state: LoginState,
    navigator: Navigator,
    onEvent: (LoginEvent) -> Unit
) {
    Drawer(navigator = navigator) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.statusBarsHeight())
                TransparentAppBar(
                    title = { Text("Вход") },
                    navigationIcon = {
                        AppBarIconBack(onNavigateUp = { onEvent(LoginEvent.BackClick) })
                    }
                )
            }

            Column {
                val passwordFocusRequest = remember { FocusRequester() }

                EditTextField(
                    text = state.email,
                    label = "E-mail",
                    error = state.emailError,
                    enabled = !state.networkCall,
                    onValueChange = { onEvent(LoginEvent.LoginTextChanged(it)) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequest.requestFocus() }
                    )
                )
                EditTextField(
                    text = state.password,
                    label = "Пароль",
                    error = state.passwordError,
                    enabled = !state.networkCall,
                    onValueChange = { onEvent(LoginEvent.PasswordTextChanged(it)) },
                    modifier = Modifier.focusRequester(passwordFocusRequest),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onEvent(LoginEvent.Login) }
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))

                ProgressButton(
                    text = "Войти",
                    progress = state.networkCall,
                    onClick = {
                        if (!state.networkCall) {
                            onEvent(LoginEvent.Login)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    enabled = !state.networkCall,
                    onClick = { onEvent(LoginEvent.RegisterClick) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Регистрация")
                }
            }

            Column {
                TextButton(
                    enabled = !state.networkCall,
                    onClick = { onEvent(LoginEvent.PasswordRestoreClick) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Забыли пароль?")
                }
                Spacer(modifier = Modifier.navigationBarsWithImePadding())
            }
        }
    }

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(LoginEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}