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

@Composable
fun SdmNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SecomiScreens.SplashScreen.name) {
        composable(SecomiScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(SecomiScreens.LoginScreen.name) {
            LoginScreen(navController)
        }
        composable(SecomiScreens.RegisterScreen.name) {
            RegisterScreen(navController)
        }
        composable(SecomiScreens.FindingAccountScreen.name) {
            FindingAccountScreen(navController)
        }


        composable(SecomiScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(SecomiScreens.HomeDetailScreen.name) {
            HomeDetailScreen(navController)
        }
        composable(SecomiScreens.HomeAddScreen.name) {
            HomeAddScreen(navController)
        }


        composable(SecomiScreens.EducationScreen.name) {
            EducationScreen(navController)
        }


        composable(SecomiScreens.EventScreen.name) {
            EventScreen(navController)
        }


        composable(SecomiScreens.MyPageScreen.name) {
            MyPageScreen(navController)
        }


        composable(SecomiScreens.RankingScreen.name) {
            RankingScreen(navController)
        }


        composable(SecomiScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(SecomiScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }


    }

}

















