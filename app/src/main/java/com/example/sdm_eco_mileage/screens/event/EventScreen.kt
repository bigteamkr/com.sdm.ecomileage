package com.example.sdm_eco_mileage.screens.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.components.SecomiBottomBar
import com.example.sdm_eco_mileage.components.SecomiMainFloatingActionButton
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.google.accompanist.systemuicontroller.SystemUiController


@Composable
fun EventScreen(navController: NavController, systemUiController: SystemUiController) {
    systemUiController.setStatusBarColor(
        Color.White
    )

    Scaffold(
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.EventScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column {
            Row() {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            if ()
        }

    }
}