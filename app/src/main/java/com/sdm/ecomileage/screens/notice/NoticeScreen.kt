package com.sdm.ecomileage.screens.notice

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.navigation.SecomiScreens

@Composable
fun NoticeScreen(navController: NavController, systemUiController: SystemUiController){
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    BackHandler() {
        navController.navigate(SecomiScreens.HomeScreen.name) {
            popUpTo(SecomiScreens.NoticeScreen.name) { inclusive = true }
        }
    }

}