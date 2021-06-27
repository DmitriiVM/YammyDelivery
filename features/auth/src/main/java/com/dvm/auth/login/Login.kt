package com.dvm.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.auth.R
import com.dvm.auth.login.model.LoginEvent
import com.dvm.auth.login.model.LoginState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Login(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    Drawer(selected = DrawerItem.NONE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(Modifier.statusBarsHeight())
                DefaultAppBar(
                    title = { Text(stringResource(R.string.login_appbar_title)) },
                    navigationIcon = {
                        AppBarIconBack(onNavigateUp = { onEvent(LoginEvent.BackClick) })
                    }
                )
            }

            Column {

                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                val passwordFocusRequest = remember { FocusRequester() }

                EditTextField(
                    text = email,
                    label = stringResource(R.string.login_field_email),
                    error = state.emailError,
                    enabled = !state.networkCall,
                    onValueChange = {
                        email = it
                        onEvent(LoginEvent.LoginTextChanged)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequest.requestFocus() }
                    )
                )
                EditTextField(
                    text = password,
                    label = stringResource(R.string.login_field_password),
                    error = state.passwordError,
                    enabled = !state.networkCall,
                    onValueChange = {
                        password = it
                        onEvent(LoginEvent.PasswordTextChanged)
                    },
                    modifier = Modifier.focusRequester(passwordFocusRequest),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEvent(
                                LoginEvent.Login(
                                    email = email,
                                    password = password,
                                )
                            )
                        }
                    )
                )
                Spacer(Modifier.height(30.dp))

                ProgressButton(
                    text = stringResource(R.string.login_button_login),
                    progress = state.networkCall,
                    onClick = {
                        if (!state.networkCall) {
                            onEvent(
                                LoginEvent.Login(
                                    email = email,
                                    password = password,
                                )
                            )
                        }
                    }
                )

                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    enabled = !state.networkCall,
                    onClick = { onEvent(LoginEvent.RegisterClick) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.login_button_registartion))
                }
            }

            Column {
                TextButton(
                    enabled = !state.networkCall,
                    onClick = { onEvent(LoginEvent.PasswordRestoreClick) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.login_button_restore_password))
                }
                Spacer(Modifier.navigationBarsWithImePadding())
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