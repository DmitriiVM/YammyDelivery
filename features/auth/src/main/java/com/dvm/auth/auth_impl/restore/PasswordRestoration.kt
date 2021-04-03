package com.dvm.auth.auth_impl.restore

import androidx.compose.runtime.Composable
import com.dvm.appmenu.Drawer
import com.dvm.appmenu.Navigator
import com.dvm.auth.auth_impl.restore.model.RestoreEvent
import com.dvm.auth.auth_impl.restore.model.RestoreState

@Composable
fun PasswordRestoration(
    state: RestoreState,
    onEvent: (RestoreEvent) -> Unit,
    navigator: Navigator,
) {
    Drawer(navigator){

    }
}