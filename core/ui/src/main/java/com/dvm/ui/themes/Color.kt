package com.dvm.ui.themes

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val LightColorPalette = lightColors(
    primary = Color(0xFFffab40)
)

internal val DarkColorPalette = darkColors(
    primary = Color(0xFF178F99)
)

enum class DecorColors(val color: Color) {
    BLUE(Color(0xFF1A95BB)),
    YELLOW(Color(0xFFC29515)),
    ORANGE(Color(0xFFCE5D18)),
    GREEN(Color(0xFF3A8F7D)),
    DARK_BLUE(Color(0xFF28669C)),
}

val violet = Color(0xFF665EFF)