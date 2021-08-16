package com.dvm.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

@Composable
fun Image(
    data: Any?,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit = { /*TODO implement loading placeholder*/ },
    error: @Composable () -> Unit = { ErrorImage() }
) {
    val painter = rememberImagePainter(data)

    Box(modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        when (painter.state) {
            is ImagePainter.State.Loading -> loading()
            is ImagePainter.State.Error -> error()
            else -> { /*ignore*/ }
        }
    }
}