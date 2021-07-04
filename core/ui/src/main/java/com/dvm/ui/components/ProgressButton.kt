package com.dvm.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProgressButton(
    text: String,
    progress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {

        ConstraintLayout {
            val (textRef, spacer, progressRef) = createRefs()

            Text(
                text = text,
                modifier = Modifier
                    .constrainAs(textRef) {
                        centerHorizontallyTo(parent)
                    }
            )

            if (progress) {
                Spacer(
                    modifier = Modifier
                        .width(15.dp)
                        .constrainAs(spacer) {
                            start.linkTo(textRef.end)
                        }
                )
                CircularProgressIndicator(
                    color = MaterialTheme.colors.surface,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(16.dp)
                        .constrainAs(progressRef) {
                            start.linkTo(spacer.end)
                            centerVerticallyTo(parent)
                        }
                )
            }
        }
    }
}