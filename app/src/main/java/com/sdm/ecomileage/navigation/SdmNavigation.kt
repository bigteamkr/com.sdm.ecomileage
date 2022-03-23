package com.sdm.ecomileage.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sdm.ecomileage.screens.education.EducationScreen
import com.sdm.ecomileage.screens.event.EventScreen
import com.sdm.ecomileage.screens.findingAccount.FindingAccountScreen
import com.sdm.ecomileage.screens.home.HomeScreen
import com.sdm.ecomileage.screens.homeAdd.HomeAddScreen
import com.sdm.ecomileage.screens.homeDetail.HomeDetailScreen
import com.sdm.ecomileage.screens.loginRegister.LoginScreen
import com.sdm.ecomileage.screens.myPage.MyPageScreen
import com.sdm.ecomileage.screens.notice.NoticeScreen
import com.sdm.ecomileage.screens.ranking.RankingScreen
import com.sdm.ecomileage.screens.search.SearchScreen
import com.sdm.ecomileage.screens.settings.SettingsScreen
import com.sdm.ecomileage.screens.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SdmNavigation() {
    val systemUiController = rememberSystemUiController()
    // Todo: systemUiController 모든 Screen 에 넣어주세요.


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SecomiScreens.SplashScreen.name) {
        composable(SecomiScreens.SplashScreen.name) {
            SplashScreen(navController, systemUiController)
        }

        composable(SecomiScreens.LoginScreen.name) {
            LoginScreen(navController, systemUiController)
        }

        composable(SecomiScreens.FindingAccountScreen.name) {
//            FindingAccountScreen(navController, systemUiController)
            FindingAccountScreen()
        }

        composable(SecomiScreens.HomeScreen.name) {
            HomeScreen(navController, systemUiController)
        }
        composable("${SecomiScreens.HomeDetailScreen.name}/{feedNo}", arguments = listOf(
            navArgument(name = "feedNo") {
                type = NavType.IntType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("feedNo").let { feedNo ->
                HomeDetailScreen(navController, systemUiController, feedNo = feedNo)
            }
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
            RankingScreen(navController, systemUiController)
        }

        composable(SecomiScreens.SearchScreen.name) {
            SearchScreen(navController, systemUiController)
        }
        composable(SecomiScreens.SettingsScreen.name) {
            SettingsScreen(navController, systemUiController)
        }

        composable(SecomiScreens.NoticeScreen.name) {
            NoticeScreen(navController, systemUiController)
        }

    }
}

















