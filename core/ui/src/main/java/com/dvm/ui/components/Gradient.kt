package com.dvm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

fun Modifier.horizontalGradient(start: Color, end: Color) =
    background(
        brush = Brush.horizontalGradient(
            listOf(
                start,
                end,
            )
        ),
        shape = RoundedCornerShape(4.dp)
    )