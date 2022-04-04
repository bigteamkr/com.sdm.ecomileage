package com.sdm.ecomileage.screens.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.components.SecomiBottomBar
import com.sdm.ecomileage.components.SecomiMainFloatingActionButton
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.SelectedTabColor
import com.sdm.ecomileage.ui.theme.UnSelectedTabColor
import kotlinx.coroutines.launch


@Composable
fun EventScreen(navController: NavController, systemUiController: SystemUiController) {
    systemUiController.setStatusBarColor(
        Color.White
    )

    EventScaffold(navController)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun EventScaffold(navController: NavController) {
    val pagerState = rememberPagerState()
    val pages = listOf("이벤트 참여", "퀴즈 챌린지")
    val scope = rememberCoroutineScope()

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
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                contentColor = SelectedTabColor
            ) {
                // Todo : 혹시 TabRow 의 높이를 조정하고 싶다면 Tab 에서 Modifier.height 를 주세요. TabRow 는 Tab 의 크기를 따릅니다.
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title) },
                        modifier = Modifier.background(Color.White),
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        selectedContentColor = SelectedTabColor,
                        unselectedContentColor = UnSelectedTabColor
                    )
                }
            }

            HorizontalPager(
                count = pages.size,
                state = pagerState
            ) { index ->
                if (pages[index] == "이벤트 참여")
                    EventAttendScaffold(navController)
                else if (pages[index] == "퀴즈 챌린지")
                    QuizChallengeScaffold(navController)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun EventAttendScaffold(navController: NavController) {
    val isEventCurrent = remember {
        mutableStateOf(true)
    }
    val pagerState = rememberPagerState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {

        HorizontalPager(
            count = 1,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = com.sdm.ecomileage.R.drawable.image_event_sample),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(vertical = 60.dp)
                    .fillMaxSize()
            )
        }

    }
}

@Composable
private fun QuizChallengeScaffold(navController: NavController) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = com.sdm.ecomileage.R.drawable.image_quiz_sample),
                contentDescription = "Sample",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(vertical = 60.dp)
            )
        }
    }
}