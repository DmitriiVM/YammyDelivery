package com.dvm.order.ordering

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.navigation.Navigator
import com.dvm.order.R
import com.dvm.order.ordering.model.OrderingEvent
import com.dvm.order.ordering.model.OrderingFields
import com.dvm.order.ordering.model.OrderingState
import com.dvm.ui.components.*
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Ordering(
    state: OrderingState,
    navigator: Navigator,
    onEvent: (OrderingEvent) -> Unit,
) {
    Drawer(
        navigator = navigator
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.ordering_appbar_title)) },
                navigationIcon = {
                    AppBarIconBack {
                        onEvent(OrderingEvent.BackClick)
                    }
                },
            )
            var fields by rememberSaveable { mutableStateOf(OrderingFields()) }

            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(ScrollState(0))
            ) {

                var isEditing by rememberSaveable { mutableStateOf(false) }

                val addressFocus = remember { FocusRequester() }
                val entranceFocus = remember { FocusRequester() }
                val floorFocus = remember { FocusRequester() }
                val apartmentFocus = remember { FocusRequester() }
                val intercomFocus = remember { FocusRequester() }
                val commentFocus = remember { FocusRequester() }

                val keyboardController = LocalSoftwareKeyboardController.current

                Spacer(Modifier.height(16.dp))
                OrderingTextField(
                    value = fields.address,
                    onValueChange = { fields = fields.copy(address = it) },
                    readOnly = !isEditing,
                    singleLine = false,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.focusRequester(addressFocus),
                    keyboardActions = KeyboardActions(
                        onNext = { entranceFocus.requestFocus() }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_address)) }
                )

                Spacer(modifier = Modifier.height(25.dp))
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        enabled = !isEditing || fields.address.isNotEmpty(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        onClick = {
                            isEditing = !isEditing
                            if (!isEditing) {
                                keyboardController?.hideSoftwareKeyboard()
                            }
                        }
                    ) {
                        if (isEditing) {
                            Text(stringResource(R.string.ordering_button_apply))
                        } else {
                            Text(stringResource(R.string.ordering_button_fill))
                        }
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp),
                        onClick = { onEvent(OrderingEvent.MapButtonClick) }
                    ) {
                        Text(stringResource(R.string.ordering_button_use_map))
                    }
                }
                Spacer(Modifier.height(8.dp))
                OrderingTextField(
                    value = fields.entrance,
                    onValueChange = { fields = fields.copy(entrance = it) },
                    readOnly = !isEditing,
                    singleLine = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.focusRequester(entranceFocus),
                    keyboardActions = KeyboardActions(
                        onNext = { floorFocus.requestFocus() }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_entrance)) }
                )
                OrderingTextField(
                    value = fields.floor,
                    onValueChange = { fields = fields.copy(floor = it) },
                    readOnly = !isEditing,
                    singleLine = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.focusRequester(floorFocus),
                    keyboardActions = KeyboardActions(
                        onNext = { apartmentFocus.requestFocus() }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_floor)) }
                )
                OrderingTextField(
                    value = fields.apartment,
                    onValueChange = { fields = fields.copy(apartment = it) },
                    readOnly = !isEditing,
                    singleLine = true,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.focusRequester(apartmentFocus),
                    keyboardActions = KeyboardActions(
                        onNext = { intercomFocus.requestFocus() }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_apartment)) }
                )
                OrderingTextField(
                    value = fields.intercom,
                    onValueChange = { fields = fields.copy(intercom = it) },
                    readOnly = !isEditing,
                    singleLine = true,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.focusRequester(intercomFocus),
                    keyboardActions = KeyboardActions(
                        onNext = { commentFocus.requestFocus() }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_intercom)) }
                )
                OrderingTextField(
                    value = fields.comment,
                    onValueChange = { fields = fields.copy(comment = it) },
                    readOnly = !isEditing,
                    singleLine = true,
                    modifier = Modifier.focusRequester(commentFocus),
                    keyboardActions = KeyboardActions(
                        onAny = {
                            isEditing = false
                            keyboardController?.hideSoftwareKeyboard()
                        }
                    ),
                    startText = { Text(stringResource(R.string.ordering_field_comment)) }
                )
            }
            Button(
                enabled = fields.address.isNotEmpty(),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .navigationBarsWithImePadding(),
                onClick = { onEvent(OrderingEvent.MakeOrder(fields)) }
            ) {
                Text(stringResource(R.string.ordering_button_create_order))
            }
        }
    }

    if (state.networkCall) {
        LoadingScrim()
    }

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { onEvent(OrderingEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = { AlertButton(onClick = onDismiss) }
        )
    }
}

@Composable
private fun OrderingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    startText: @Composable () -> Unit
) {
    Spacer(Modifier.height(8.dp))
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 4.dp),
        readOnly = readOnly,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row {
                startText()
                innerTextField()
            }
        }
    )
    Divider()
}