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
    NavHost(navController = navController, startDestination = SdmScreens.HomeScreen.name) {
        composable(SdmScreens.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(SdmScreens.LoginScreen.name) {
            LoginScreen(navController)
        }
        composable(SdmScreens.RegisterScreen.name) {
            RegisterScreen(navController)
        }
        composable(SdmScreens.FindingAccountScreen.name) {
            FindingAccountScreen(navController)
        }


        composable(SdmScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(SdmScreens.HomeDetailScreen.name) {
            HomeScreen(navController)
        }
        composable(SdmScreens.HomeAddScreen.name) {
            HomeDetailScreen(navController)
        }


        composable(SdmScreens.EducationScreen.name) {
            EducationScreen(navController)
        }


        composable(SdmScreens.EventScreen.name) {
            EventScreen(navController)
        }


        composable(SdmScreens.MyPageScreen.name) {
            MyPageScreen(navController)
        }


        composable(SdmScreens.RankingScreen.name) {
            RankingScreen(navController)
        }


        composable(SdmScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(SdmScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }


    }

}

















