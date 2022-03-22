package com.sdm.ecomileage.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sdm.ecomileage.R

// Set of Material typography styles to start with

val Pretendard = FontFamily(
    Font(R.font.pretendard_normal),
    Font(R.font.pretendard_black),
    Font(R.font.pretendard_bold),
    Font(R.font.pretendard_extra_bold),
    Font(R.font.pretendard_extra_light),
    Font(R.font.pretendard_light),
    Font(R.font.pretendard_medium),
    Font(R.font.pretendard_semi_bold),
    Font(R.font.pretendard_thin),
)


val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Pretendard
    ),
    h2 = TextStyle(
      fontFamily = Pretendard
    ),
    h3 = TextStyle(
        fontFamily = Pretendard
    ),
    h4 = TextStyle(
        fontFamily = Pretendard
    ),
    h5 = TextStyle(
        fontFamily = Pretendard
    ),
    h6 = TextStyle(
        fontFamily = Pretendard
    ),
    body1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Pretendard
    ),
    subtitle1 = TextStyle(
        fontFamily = Pretendard
    ),
    subtitle2 = TextStyle(
        fontFamily = Pretendard
    ),
    button = TextStyle(
        fontFamily = Pretendard
    ),
    caption = TextStyle(
        fontFamily = Pretendard
    ),
    overline = TextStyle()
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

