package com.dvm.ui.themes

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF03DAC5)

val cyan400 = Color(0xFF26c6da)
val cyan700 = Color(0xFF0095a8)
val orange400 = Color(0xFFffab40)
val orange700 = Color(0xFFc77800)

// temp
val cyan__ = Color(0xFFF0F9FA)
val bg_temp = Color(0xFFEBEBEB)
val onPrimary_temp = Color(0xFF00282E)
val onSurface_temp = Color(0xFF00545F)

// accent colors
//val light_blue = Color(0xFFCCEBFF)
//val light_yellow = Color(0xFFFFF0C6)
//val light_orange = Color(0xFFFFE5D5)
//val light_green = Color(0xFFD0FFF5)
//val light_violet = Color(0xFFD6D4FF)

internal val LightColorPalette = lightColors(
    primary = orange400,
    primaryVariant = orange700,
    secondary = orange400,
    secondaryVariant = orange700,
    background = Color.White,
    surface = Color.White,
//error,
    onPrimary = onPrimary_temp,
//onSecondary,
//onBackground,
    onSurface = onSurface_temp,
//onError,
)

internal val DarkColorPalette = darkColors(
//primary,
//primaryVariant,
//secondary,
//secondaryVariant,
//background,
//surface,
//error,
//onPrimary,
//onSecondary,
//onBackground,
//onSurface,
//onError,
)


val light_blue = Color(0xFF4DB9FF)
val light_yellow = Color(0xFFFFD150)
val light_orange = Color(0xFFFF9858)
val light_green = Color(0xFF62FFDE)
val light_violet = Color(0xFF665EFF)

val temp = Color(0xFF286FAD)

enum class AccentColors(val color: Color){
    BLUE(light_blue),
    YELLOW(light_yellow),
    ORANGE(light_orange),
    GREEN(light_green),
    VIOLET(light_violet),
}