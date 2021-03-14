package com.dvm.ui.components

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalGradient(color: Color) =
    background(
        brush = Brush.verticalGradient(
            listOf(
                Color.White,
                Color.White,
                color,
                color,
            )
        )
    )