package com.sdm.ecomileage.components.appBarComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.navigation.SecomiScreens

@Composable
fun SearchComponent(navController: NavController, currentScreens: String) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = "검색하기",
        modifier = Modifier
            .size(25.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(SecomiScreens.SearchScreen.name) {
                    popUpTo(currentScreens) { inclusive = true }
                }
            },
        tint = Color.Black
    )
}

@Composable
fun RankingComponent(navController: NavController, currentScreens: String) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        painter = painterResource(id = R.drawable.ic_ranking),
        contentDescription = "랭킹화면으로 가기",
        modifier = Modifier
            .size(25.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(SecomiScreens.MileageRanking.name) {
                    launchSingleTop = true
                }
            },
        tint = Color.Black
    )
}

@Composable
fun AlarmComponent(
    navController: NavController,
    pushImage: Int = R.drawable.ic_push_off,

    currentScreens: String,
    tint: Color = Color.Black
) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        painter = painterResource(id = pushImage),
        contentDescription = "랭킹화면으로 가기",
        modifier = Modifier
            .size(25.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { },
        tint = tint
    )
}

@Composable
fun MoreVertComponent(
    navController: NavController,
    options: List<@Composable () -> Unit>
) {
    var isShowingOptions by remember {
        mutableStateOf(false)
    }

    Icon(
        painter = painterResource(id = R.drawable.ic_more),
        contentDescription = "옵션 확인하기",
        modifier = Modifier
            .size(30.dp)
            .clickable {
                isShowingOptions = true
            },
        tint = Color.White
    )

    DropdownMenu(
        expanded = isShowingOptions,
        onDismissRequest = { isShowingOptions = false },
        modifier = Modifier.clickable(enabled = false) {}
    ) {
        options.forEach {
            it()
        }
    }
}

@Composable
fun MileageComponent(current: String, change: (String) -> Unit) {

}