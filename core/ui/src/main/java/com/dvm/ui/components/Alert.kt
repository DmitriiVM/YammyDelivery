package com.dvm.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Alert(
    message: String,
    onDismiss: () -> Unit,
    buttons: @Composable () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = message,
                Modifier.padding(bottom = 25.dp)
            )
        },
        modifier = Modifier
            .width(350.dp)
            .padding(10.dp),
        buttons = buttons,
        onDismissRequest = onDismiss,
    )
}

@Composable
fun AlertButtonOk(
    onDismiss: () -> Unit
) {
    TextButton(onClick = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 15.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text("Ок")
        }
    }
}