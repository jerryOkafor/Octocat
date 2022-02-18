package com.jerryokafor.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jerryokafor.compose.R

val Roboto = FontFamily.Default

private val appFontFamily = FontFamily(
    fonts = listOf(
        Font(resId = R.font.roboto, style = FontStyle.Normal, weight = FontWeight.Normal),

        Font(resId = R.font.roboto_bold, style = FontStyle.Normal, weight = FontWeight.Bold),
        Font(resId = R.font.roboto_bold_italic, style = FontStyle.Italic, weight = FontWeight.Bold),

        Font(resId = R.font.roboto_black, style = FontStyle.Normal, weight = FontWeight.Black),
        Font(
            resId = R.font.roboto_black_italic,
            style = FontStyle.Italic,
            weight = FontWeight.Black
        ),

        Font(resId = R.font.roboto_light, style = FontStyle.Normal, weight = FontWeight.Light),
        Font(
            resId = R.font.roboto_light_italic,
            style = FontStyle.Italic,
            weight = FontWeight.Light
        ),

        Font(resId = R.font.roboto_medium, style = FontStyle.Normal, weight = FontWeight.Medium),
        Font(
            resId = R.font.roboto_medium_italic,
            style = FontStyle.Italic,
            weight = FontWeight.Medium
        ),

        Font(resId = R.font.roboto_thin, style = FontStyle.Normal, weight = FontWeight.Thin),
        Font(resId = R.font.roboto_thin_italic, style = FontStyle.Italic, weight = FontWeight.Thin),
    )
)

private val defaultTypography = Typography()
val AppTypography = Typography(
    h1 = defaultTypography.h1.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    h2 = defaultTypography.h2.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    h3 = defaultTypography.h3.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    h4 = defaultTypography.h4.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    h5 = defaultTypography.h5.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    h6 = defaultTypography.h6.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    subtitle1 = defaultTypography.subtitle1.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    subtitle2 = defaultTypography.subtitle2.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    body1 = defaultTypography.body1.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    body2 = defaultTypography.body2.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    button = defaultTypography.button.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    caption = defaultTypography.caption.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    overline = defaultTypography.overline.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    )
)


//val AppTypography = Typography(
//    displayLarge = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 57.sp,
//        lineHeight = 64.sp,
//        letterSpacing = -0.25.sp,
//    ),
//    displayMedium = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 45.sp,
//        lineHeight = 52.sp,
//        letterSpacing = 0.sp,
//    ),
//    displaySmall = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 36.sp,
//        lineHeight = 44.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineLarge = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineMedium = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 28.sp,
//        lineHeight = 36.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineSmall = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleLarge = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleMedium = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.Medium,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    titleSmall = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    labelLarge = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.25.sp,
//    ),
//    bodySmall = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.W400,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.4.sp,
//    ),
//    labelMedium = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.Medium,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    labelSmall = TextStyle(
//        fontFamily = Roboto,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
//    ),
//)
