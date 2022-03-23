package com.sdm.ecomileage.screens.notice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun NoticeScreen(navController: NavController, systemUiController: SystemUiController){
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

}