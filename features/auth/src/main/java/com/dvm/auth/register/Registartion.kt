package com.dvm.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
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
import com.dvm.auth.register.model.RegisterEvent
import com.dvm.auth.register.model.RegisterState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun Registration(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    Drawer(selected = DrawerItem.NONE) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.registration_appbar_title)) },
                navigationIcon = {
                    AppBarIconBack(onNavigateUp = { onEvent(RegisterEvent.Back) })
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                var firstName by rememberSaveable { mutableStateOf("") }
                var lastName by rememberSaveable { mutableStateOf("") }
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                val lastNameFocus = remember { FocusRequester() }
                val emailFocus = remember { FocusRequester() }
                val passwordFocus = remember { FocusRequester() }

                EditTextField(
                    text = firstName,
                    label = stringResource(R.string.registration_field_name),
                    error = state.firstNameError,
                    enabled = !state.progress,
                    onValueChange = {
                        firstName = it
                        onEvent(RegisterEvent.ChangeFirstName)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { lastNameFocus.requestFocus() }
                    )
                )
                EditTextField(
                    text = lastName,
                    label = stringResource(R.string.registration_field_last_name),
                    error = state.lastNameError,
                    enabled = !state.progress,
                    onValueChange = {
                        lastName = it
                        onEvent(RegisterEvent.ChangeLastName)
                    },
                    modifier = Modifier.focusRequester(lastNameFocus),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { emailFocus.requestFocus() }
                    )
                )
                EditTextField(
                    text = email,
                    label = stringResource(R.string.registration_field_email),
                    error = state.emailError,
                    enabled = !state.progress,
                    onValueChange = {
                        email = it
                        onEvent(RegisterEvent.ChangeEmail)
                    },
                    modifier = Modifier.focusRequester(emailFocus),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocus.requestFocus() }
                    )
                )
                EditTextField(
                    text = password,
                    label = stringResource(R.string.registration_field_password),
                    error = state.passwordError,
                    enabled = !state.progress,
                    onValueChange = {
                        password = it
                        onEvent(RegisterEvent.ChangePassword)
                    },
                    modifier = Modifier.focusRequester(passwordFocus),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEvent(
                                RegisterEvent.Register(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email,
                                    password = password,
                                )
                            )
                        }
                    )
                )

                Spacer(Modifier.height(10.dp))
                ProgressButton(
                    text = stringResource(R.string.registration_button_register),
                    progress = state.progress,
                    onClick = {
                        onEvent(
                            RegisterEvent.Register(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                            )
                        )
                    }
                )
                Spacer(Modifier.height(15.dp))
                OutlinedButton(
                    enabled = !state.progress,
                    onClick = { onEvent(RegisterEvent.Login) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsWithImePadding()
                ) {
                    Text(text = stringResource(R.string.registration_button_login))
                }
                Spacer(Modifier.height(100.dp))
            }
        }
    }

    if (!state.alert.isNullOrEmpty()) {
        val onDismiss = { onEvent(RegisterEvent.DismissAlert) }
        Alert(
            message = state.alert,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}

