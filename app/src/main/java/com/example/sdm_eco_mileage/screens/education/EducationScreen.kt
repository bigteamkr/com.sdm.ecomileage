package com.example.sdm_eco_mileage.screens.education

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.CardContent
import com.example.sdm_eco_mileage.components.SecomiBottomBar
import com.example.sdm_eco_mileage.components.SecomiMainFloatingActionButton
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.TopBarColor

@Composable
fun EducationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "연북중학교",
                navController = navController,
                currentScreen = SecomiScreens.EducationScreen.name,
                backgroundColor = TopBarColor,
                actionIconsList = listOf(
                    painterResource(id = R.drawable.ic_push_on)
                ),
            )
        },
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.EducationScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column {

        }
    }
}