package com.example.sdm_eco_mileage.screens.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.components.SecomiBottomBar
import com.example.sdm_eco_mileage.components.SecomiMainFloatingActionButton
import com.example.sdm_eco_mileage.data.EventCurrentSampleData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.SelectedTabColor
import com.example.sdm_eco_mileage.ui.theme.UnSelectedTabColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
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

        Column() {
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

    val sampleCurrentEventItems = EventCurrentSampleData

    Scaffold {

        HorizontalPager(
            count = sampleCurrentEventItems.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = rememberImagePainter(sampleCurrentEventItems[page]),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }

    }
}

@Composable
private fun QuizChallengeScaffold(navController: NavController) {
    Scaffold() {

        Text(text = "Hi")
    }
}