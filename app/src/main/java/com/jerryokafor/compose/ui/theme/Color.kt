package com.jerryokafor.compose.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val colorPrimary = Color(0xFF2E3D49)
private val colorLightPrimary = Color(0xFF586775)
private val colorLightTextPrimary = Color(0xFF000000)
private val colorLightTextSecondary = Color(0xFF6C727A)
private val colorLightBackground = Color(0xFFFFFFFF)
private val colorLightError = Color(0xFFD62222)

private val colorDarkPrimary = Color(0xFF061721)
private val colorDarkTextPrimary = Color(0xFFFAFAFA)
private val colorDarkTextSecondary = Color(0xFF6C727A)
private val colorDarkBackground = Color(0xFF090A0A)
private val colorDarkError = Color(0xFFD62222)

class AppColors(
    primary: Color,
    textPrimary: Color,
    textSecondary: Color,
    background: Color,
    error: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var error by mutableStateOf(error)
        private set
    var background by mutableStateOf(background)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        primary: Color = this.primary,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        error: Color = this.error,
        background: Color = this.background,
        isLight: Boolean = this.isLight
    ): AppColors = AppColors(
        primary,
        textPrimary,
        textSecondary,
        error,
        background,
        isLight
    )

    fun updateColorsFrom(other: AppColors) {
        primary = other.primary
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        background = other.background
        error = other.error
    }
}

fun lightColors(
    primary: Color = colorLightPrimary,
    textPrimary: Color = colorLightTextPrimary,
    textSecondary: Color = colorLightTextSecondary,
    background: Color = colorLightBackground,
    error: Color = colorLightError
): AppColors = AppColors(
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    background = background,
    error = error,
    isLight = true
)

fun darkColors(
    primary: Color = colorDarkPrimary,
    textPrimary: Color = colorDarkTextPrimary,
    textSecondary: Color = colorDarkTextSecondary,
    background: Color = colorDarkBackground,
    error: Color = colorDarkError
): AppColors = AppColors(
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    background = background,
    error = error,
    isLight = false
)

val LocalColors = staticCompositionLocalOf { lightColors() }
