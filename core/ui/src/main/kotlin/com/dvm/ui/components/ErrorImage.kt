package com.dvm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dvm.ui.R

@Composable
fun ErrorImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.icon_camera),
        contentDescription = null,
        alpha = 0.3f,
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}