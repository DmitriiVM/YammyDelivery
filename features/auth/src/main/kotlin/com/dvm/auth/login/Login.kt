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
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsHeight
import org.koin.androidx.compose.getStateViewModel

@Composable
internal fun Login(
    viewModel: LoginViewModel = getStateViewModel()
) {
    val state: LoginState = viewModel.state
    val onEvent: (LoginEvent) -> Unit = { viewModel.dispatch(it) }

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
                        AppBarIconBack(onNavigateUp = { onEvent(LoginEvent.Back) })
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
                    enabled = !state.progress,
                    onValueChange = {
                        email = it
                        onEvent(LoginEvent.ChangeLogin)
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
                    enabled = !state.progress,
                    onValueChange = {
                        password = it
                        onEvent(LoginEvent.ChangePassword)
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
                    progress = state.progress,
                    onClick = {
                        if (!state.progress) {
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
                    enabled = !state.progress,
                    onClick = { onEvent(LoginEvent.Register) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.login_button_registartion))
                }
            }

            Column {
                TextButton(
                    enabled = !state.progress,
                    onClick = { onEvent(LoginEvent.RestorePassword) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.login_button_restore_password))
                }
                Spacer(Modifier.navigationBarsWithImePadding())
            }
        }
    }

    if (state.alert != null) {
        val onDismiss = { onEvent(LoginEvent.DismissAlert) }
        Alert(
            message = stringResource(state.alert),
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}