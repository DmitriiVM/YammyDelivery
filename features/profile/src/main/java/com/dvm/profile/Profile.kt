package com.dvm.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dvm.appmenu_api.Drawer
import com.dvm.profile.model.ProfileEvent
import com.dvm.profile.model.ProfileState
import com.dvm.ui.components.*
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Profile(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.PROFILE
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.profile_appbar_title)) },
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
                    label = stringResource(R.string.profile_text_field_name),
                    error = state.firstNameError,
                    enabled = !state.networkCall && state.isEditing,
                    readOnly = state.networkCall || !state.isEditing,
                    onValueChange = { onEvent(ProfileEvent.FirstNameTextChanged(it)) },
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
                    label = stringResource(R.string.profile_text_field_last_name),
                    error = state.lastNameError,
                    enabled = !state.networkCall && state.isEditing,
                    readOnly = state.networkCall || !state.isEditing,
                    onValueChange = { onEvent(ProfileEvent.LastNameTextChanged(it)) },
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
                    label = stringResource(R.string.profile_text_field_email),
                    error = state.emailError,
                    enabled = !state.networkCall && state.isEditing,
                    readOnly = state.networkCall || !state.isEditing,
                    onValueChange = { onEvent(ProfileEvent.EmailTextChanged(it)) },
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
                if (state.isEditing) {
                    ProgressButton(
                        stringResource(R.string.profile_button_save),
                        progress = state.networkCall,
                        onClick = { onEvent(ProfileEvent.SaveProfile) }
                    )
                } else {
                    ProgressButton(
                        stringResource(R.string.profile_button_change_mode),
                        progress = state.networkCall,
                        onClick = { onEvent(ProfileEvent.ChangeEditingMode(true)) }
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                if (state.isEditing) {
                    OutlinedButton(
                        enabled = !state.networkCall,
                        onClick = {
                            onEvent(ProfileEvent.ChangeEditingMode(false))
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsWithImePadding()
                    ) {
                        Text(stringResource(R.string.profile_button_cancel))
                    }
                } else {
                    OutlinedButton(
                        enabled = !state.networkCall,
                        onClick = { onEvent(ProfileEvent.ChangeButtonClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsWithImePadding()
                    ) {
                        Text(stringResource(R.string.profile_button_change_password))
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
            properties = if (state.networkCall) {
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
                        stringResource(R.string.profile_dialog_title),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(stringResource(R.string.profile_dialog_field_new_password))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.networkCall,
                        value = newPassword,
                        onValueChange = { newPassword = it }
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(stringResource(R.string.profile_dialog_field_old_password))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.networkCall,
                        value = oldPassword,
                        onValueChange = { oldPassword = it }
                    )
                    Spacer(Modifier.height(30.dp))
                    ProgressButton(
                        text = stringResource(R.string.profile_dialog_button_save),
                        progress = state.networkCall,
                        enabled = newPassword.isNotEmpty() && oldPassword.isNotEmpty() && !state.networkCall,
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

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(ProfileEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}