package com.dvm.auth.restore

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvm.appmenu_api.Drawer
import com.dvm.auth.R
import com.dvm.auth.restore.model.RestoreEvent
import com.dvm.auth.restore.model.RestoreState
import com.dvm.auth.restore.model.Screen
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import com.google.accompanist.insets.statusBarsHeight

@Composable
internal fun PasswordRestoreScreen(
    viewModel: PasswordRestoreViewModel = hiltViewModel()
) {
    val state: RestoreState = viewModel.state
    val onEvent: (RestoreEvent) -> Unit = { viewModel.dispatch(it) }

    val email = rememberSaveable { mutableStateOf("") }
    val code = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }

    Drawer(selected = DrawerItem.NONE) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.password_restoration_appbar_title)) },
                navigationIcon = {
                    AppBarIconBack { onEvent(RestoreEvent.Back) }
                },
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                when (state.screen) {
                    Screen.EMAIL ->
                        Email(
                            email = email.value,
                            onEmailChanged = { email.value = it },
                            onSend = { onEvent(RestoreEvent.SendEmail(email.value)) }
                        )
                    Screen.CODE ->
                        Code(
                            code = code.value,
                            onCodeChanged = { code.value = it },
                            onComplete = {
                                onEvent(
                                    RestoreEvent.VerifyCode(
                                        email.value,
                                        code.value
                                    )
                                )
                            }
                        )
                    Screen.PASSWORD ->
                        Password(
                            password = password.value,
                            confirmPassword = confirmPassword.value,
                            onPasswordChanged = { password.value = it },
                            onConfirmPasswordChanged = { confirmPassword.value = it },
                            onSave = {
                                onEvent(
                                    RestoreEvent.ResetPassword(
                                        email = email.value,
                                        code = code.value,
                                        password = password.value
                                    )
                                )
                            }
                        )
                }
            }
        }
    }

    if (state.progress) {
        LoadingScrim()
    }

    if (state.alert != null) {
        val onDismiss = { onEvent(RestoreEvent.DismissAlert) }
        Alert(
            message = stringResource(state.alert),
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}