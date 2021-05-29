package com.dvm.ui.themes

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val LightColorPalette = lightColors(
    primary = Color(0xFFffab40)
)

internal val DarkColorPalette = darkColors(
    primary = Color(0xFF22B8C5)
)

enum class DecorColors(val color: Color){
    BLUE(Color(0xFF4DB9FF)),
    YELLOW(Color(0xFFFFD150)),
    ORANGE(Color(0xFFFF9858)),
    GREEN(Color(0xFF62FFDE)),
    VIOLET(Color(0xFF2C79BD)),
    LIGHT_BLUE(Color(0xFFC1E7FF)),
    LIGHT_YELLOW(Color(0xFFFFF4D4)),
    LIGHT_ORANGE(Color(0xFFFFE3D2)),
    LIGHT_GREEN(Color(0xFFDFFFF8)),
    LIGHT_VIOLET(Color(0xFFCBE7FF)),
}