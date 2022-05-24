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
import com.sdm.ecomileage.screens.diary.DiaryScreen
import com.sdm.ecomileage.screens.education.EducationScreen
import com.sdm.ecomileage.screens.educationVideo.EducationVideoScreen
import com.sdm.ecomileage.screens.event.EventScreen
import com.sdm.ecomileage.screens.findingAccount.FindingAccountScreen
import com.sdm.ecomileage.screens.home.HomeScreen
import com.sdm.ecomileage.screens.homeAdd.HomeAddScreen
import com.sdm.ecomileage.screens.homeDetail.HomeDetailScreen
import com.sdm.ecomileage.screens.loginRegister.LoginScreen
import com.sdm.ecomileage.screens.mileage.MileageScreen
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

        composable(SecomiScreens.LoginScreen.name + "/{type}", arguments = listOf(
            navArgument(name = "type") {
                type = NavType.IntType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("type")?.let { type ->
                LoginScreen(navController, systemUiController, type)
            }
        }

        composable("${SecomiScreens.FindingAccountScreen.name}/{type}", arguments = listOf(
            navArgument(name = "type") {
                type = NavType.IntType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("type")?.let { type ->
                FindingAccountScreen(navController, type)
            }
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

        composable("${SecomiScreens.EducationVideoScreen.name}/{videoURL}&{thumbnailURL}&{point}&{educationNo}&{educationYN}&{manageProfile}&{manageId}&{manageName}") { navBackStackEntry ->

            val videoURL = navBackStackEntry.arguments?.getString("videoURL")
            val thumbnail = navBackStackEntry.arguments?.getString("thumbnailURL")
            val point = navBackStackEntry.arguments?.getInt("point")
            val educationNo = navBackStackEntry.arguments?.getInt("educationNo")
            val educationYN = navBackStackEntry.arguments?.getBoolean("educationYN")
            val manageProfile = navBackStackEntry.arguments?.getString("manageProfile")
            val manageId = navBackStackEntry.arguments?.getString("manageId")
            val manageName = navBackStackEntry.arguments?.getString("manageName")

            EducationVideoScreen(
                videoURL ?: "ERROR",
                thumbnail ?: "ERROR",
                point ?: -1,
                educationNo ?: -1,
                educationYN ?: false,
                manageProfile ?: "ERROR",
                manageId ?: "ERROR",
                manageName ?: "ERROR",
                navController = navController
            )
        }

        composable("${SecomiScreens.DiaryScreen.name}/{educationNo}", arguments = listOf(
            navArgument(name = "educationNo") {
                type = NavType.IntType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("educationNo").let { educationNo ->
                DiaryScreen(navController, systemUiController, educationNo)
            }
        }


        composable(SecomiScreens.EventScreen.name) {
            EventScreen(navController, systemUiController)
        }


        composable("${SecomiScreens.MyPageScreen.name}/{userId}", arguments = listOf(
            navArgument(name = "userId") {
                type = NavType.StringType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("userId").let { userId ->
                MyPageScreen(navController, systemUiController, userId)
            }
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

        composable(SecomiScreens.MileageRanking.name) {
            MileageScreen(navController, systemUiController)
        }

    }
}
