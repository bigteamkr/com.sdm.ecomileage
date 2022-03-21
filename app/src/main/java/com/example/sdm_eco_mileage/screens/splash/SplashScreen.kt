package com.example.sdm_eco_mileage.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.PointColor
import com.example.sdm_eco_mileage.ui.theme.SplashColor
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(
    navController: NavController,
    systemUiController: SystemUiController
) {
    //Todo : Image 바깥 쪽 패딩 정리

    SideEffect {
        systemUiController.setStatusBarColor(
            color = PointColor
        )
    }

    val gradient = Brush.linearGradient(
        colors = SplashColor,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    val visible = remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current

    LaunchedEffect(key1 = true) {
        delay(500)
        visible.value = true
        delay(3000)
        navController.navigate(SecomiScreens.LoginScreen.name) {
            popUpTo(SecomiScreens.SplashScreen.name) { inclusive = true }
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = visible.value,
                    enter = slideInVertically {
                        // Slide in from 40 dp from the top.
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        // Expand from the top.
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        // Fade in with the initial alpha of 0.3f.
                        initialAlpha = 0.3f
                    )
                ) {
                    SplashImage()
                }
                SplashAppName()
            }
        }
    )
}

@Composable
private fun SplashImage() {
    Icon(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "Splash Logo - Education",
        modifier = Modifier
            .height(128.dp)
            .width(72.dp),
        tint = Color.White
    )
}

@Preview
@Composable
private fun SplashAppName() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "S E C O M I",
            style = MaterialTheme.typography.h4,
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Justify,
            letterSpacing = 5.5.sp,
            color = Color.White,
        )

        Text(
            text = "당신의 에코 라이프를 펼쳐보세요",
            modifier = Modifier.padding(start = 1.5.dp),
            style = MaterialTheme.typography.subtitle1,
            letterSpacing = 4.sp,
            fontSize = 12.sp,
            textAlign = TextAlign.Justify,
            color = Color.White
        )
    }
}

