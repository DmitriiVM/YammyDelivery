package com.dvm.auth_impl.presentation.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.auth_impl.presentation.restore.model.RestoreEvent
import com.dvm.auth_impl.presentation.restore.model.RestoreState
import com.dvm.auth_impl.presentation.restore.model.Screen
import com.dvm.drawer_api.DrawerLauncher
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.DefaultAppBar
import com.dvm.ui.components.LoadingScrim
import com.dvm.utils.DrawerItem
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getStateViewModel
import com.dvm.ui.R as CoreR

@Composable
internal fun PasswordRestoreScreen(
    viewModel: PasswordRestoreViewModel = getStateViewModel(),
    drawerLauncher: DrawerLauncher = get()
) {
    val state: RestoreState = viewModel.state

    val email = rememberSaveable { mutableStateOf("") }
    val code = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }

    drawerLauncher.Drawer(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        selected = DrawerItem.NONE
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            DefaultAppBar(
                title = { Text(stringResource(CoreR.string.password_restoration_appbar_title)) },
                navigationIcon = {
                    AppBarIconBack { viewModel.dispatch(RestoreEvent.Back) }
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
                            onSend = {
                                viewModel.dispatch(RestoreEvent.SendEmail(email.value))
                            }
                        )
                    Screen.CODE ->
                        Code(
                            code = code.value,
                            onCodeChanged = { code.value = it },
                            onComplete = {
                                viewModel.dispatch(
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
                                viewModel.dispatch(
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
        val onDismiss = { viewModel.dispatch(RestoreEvent.DismissAlert) }
        Alert(
            message = stringResource(state.alert),
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}