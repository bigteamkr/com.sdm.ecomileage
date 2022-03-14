package com.example.sdm_eco_mileage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sdm_eco_mileage.screens.education.EducationScreen
import com.example.sdm_eco_mileage.screens.event.EventScreen
import com.example.sdm_eco_mileage.screens.findingAccount.FindingAccountScreen
import com.example.sdm_eco_mileage.screens.home.HomeScreen
import com.example.sdm_eco_mileage.screens.homeAdd.HomeAddScreen
import com.example.sdm_eco_mileage.screens.homeDetail.HomeDetailScreen
import com.example.sdm_eco_mileage.screens.login.LoginScreen
import com.example.sdm_eco_mileage.screens.myPage.MyPageScreen
import com.example.sdm_eco_mileage.screens.ranking.RankingScreen
import com.example.sdm_eco_mileage.screens.register.RegisterScreen
import com.example.sdm_eco_mileage.screens.search.SearchScreen
import com.example.sdm_eco_mileage.screens.settings.SettingsScreen
import com.example.sdm_eco_mileage.screens.splash.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SdmNavigation() {
    val systemUiController = rememberSystemUiController()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SecomiScreens.LoginScreen.name) {
        composable(SecomiScreens.SplashScreen.name) {
            SplashScreen(navController, systemUiController)
        }

        composable(SecomiScreens.LoginScreen.name) {
            LoginScreen(navController, systemUiController)
        }
        composable(SecomiScreens.RegisterScreen.name) {
//            RegisterScreen(navController, systemUiController)
            RegisterScreen()
        }
        composable(SecomiScreens.FindingAccountScreen.name) {
//            FindingAccountScreen(navController, systemUiController)
            FindingAccountScreen()
        }


        composable(SecomiScreens.HomeScreen.name) {
            HomeScreen(navController, systemUiController)
        }
        composable(SecomiScreens.HomeDetailScreen.name) {
            HomeDetailScreen(navController, systemUiController)
        }
        composable(SecomiScreens.HomeAddScreen.name) {
            HomeAddScreen(navController, systemUiController)
        }


        composable(SecomiScreens.EducationScreen.name) {
            EducationScreen(navController, systemUiController)
        }


        composable(SecomiScreens.EventScreen.name) {
            EventScreen(navController, systemUiController)
        }


        composable(SecomiScreens.MyPageScreen.name) {
            MyPageScreen(navController, systemUiController)
        }


        composable(SecomiScreens.RankingScreen.name) {
//            RankingScreen(navController, systemUiController)
            RankingScreen()
        }


        composable(SecomiScreens.SearchScreen.name) {
            SearchScreen(navController, systemUiController)
        }
        composable(SecomiScreens.SettingsScreen.name) {
            SettingsScreen(navController, systemUiController)
        }


    }

}

















