package com.dvm.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvm.appmenu_api.Drawer
import com.dvm.profile.model.ProfileEvent
import com.dvm.profile.model.ProfileState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state: ProfileState = viewModel.state
    val onEvent: (ProfileEvent) -> Unit = { viewModel.dispatch(it) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.PROFILE
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(CoreR.string.profile_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                            keyboardController?.hide()
                        }
                    }
                },
            )
            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                val lastNameFocus = remember { FocusRequester() }
                val emailFocus = remember { FocusRequester() }

                EditTextField(
                    state.firstName,
                    label = stringResource(CoreR.string.profile_text_field_name),
                    error = state.firstNameError,
                    enabled = !state.progress && state.editing,
                    readOnly = state.progress || !state.editing,
                    onValueChange = { onEvent(ProfileEvent.ChangeFirstName(it)) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { lastNameFocus.requestFocus() }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                    )
                )
                EditTextField(
                    state.lastName,
                    label = stringResource(CoreR.string.profile_text_field_last_name),
                    error = state.lastNameError,
                    enabled = !state.progress && state.editing,
                    readOnly = state.progress || !state.editing,
                    onValueChange = { onEvent(ProfileEvent.ChangeLastName(it)) },
                    modifier = Modifier.focusRequester(lastNameFocus),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { emailFocus.requestFocus() }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                    )
                )
                EditTextField(
                    state.email,
                    label = stringResource(CoreR.string.profile_text_field_email),
                    error = state.emailError,
                    enabled = !state.progress && state.editing,
                    readOnly = state.progress || !state.editing,
                    onValueChange = { onEvent(ProfileEvent.ChangeEmailText(it)) },
                    modifier = Modifier.focusRequester(emailFocus),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onEvent(ProfileEvent.SaveProfile) }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
                if (state.editing) {
                    ProgressButton(
                        stringResource(CoreR.string.profile_button_save),
                        progress = state.progress,
                        onClick = { onEvent(ProfileEvent.SaveProfile) }
                    )
                } else {
                    ProgressButton(
                        stringResource(CoreR.string.profile_button_change_mode),
                        progress = state.progress,
                        onClick = { onEvent(ProfileEvent.ChangeEditingMode(true)) }
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                val configuration = LocalConfiguration.current

                val modifier = when (configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> Modifier.navigationBarsWithImePadding()
                    else -> Modifier
                }

                if (state.editing) {
                    OutlinedButton(
                        enabled = !state.progress,
                        onClick = {
                            onEvent(ProfileEvent.ChangeEditingMode(false))
                            keyboardController?.hide()
                        },
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(CoreR.string.profile_button_cancel))
                    }
                } else {
                    OutlinedButton(
                        enabled = !state.progress,
                        onClick = { onEvent(ProfileEvent.EditPassword) },
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(CoreR.string.profile_button_change_password))
                    }
                }
                Spacer(Modifier.height(100.dp))
            }
        }
    }

    if (state.passwordChanging) {
        Dialog(
            onDismissRequest = {
                onEvent(ProfileEvent.DismissPasswordDialog)
            },
            properties = if (state.progress) {
                DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            } else {
                DialogProperties()
            }
        ) {
            Surface(shape = MaterialTheme.shapes.medium) {
                Column(Modifier.padding(15.dp)) {

                    var newPassword by rememberSaveable { mutableStateOf("") }
                    var oldPassword by rememberSaveable { mutableStateOf("") }

                    Text(
                        stringResource(CoreR.string.profile_dialog_title),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(stringResource(CoreR.string.profile_dialog_field_new_password))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.progress,
                        value = newPassword,
                        onValueChange = { newPassword = it }
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(stringResource(CoreR.string.profile_dialog_field_old_password))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.progress,
                        value = oldPassword,
                        onValueChange = { oldPassword = it }
                    )
                    Spacer(Modifier.height(30.dp))
                    ProgressButton(
                        text = stringResource(CoreR.string.profile_dialog_button_save),
                        progress = state.progress,
                        enabled = newPassword.isNotEmpty() && oldPassword.isNotEmpty() && !state.progress,
                        onClick = {
                            onEvent(
                                ProfileEvent.ChangePassword(
                                    newPassword = newPassword,
                                    oldPassword = oldPassword
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    if (state.alert != null) {
        val onDismiss = { onEvent(ProfileEvent.DismissAlert) }
        Alert(
            message = stringResource(state.alert),
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}