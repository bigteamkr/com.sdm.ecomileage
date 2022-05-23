package com.sdm.ecomileage.screens.event

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiBottomBar
import com.sdm.ecomileage.components.SecomiMainFloatingActionButton
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun EventScreen(navController: NavController, systemUiController: SystemUiController) {
    SideEffect {
        systemUiController.setStatusBarColor(
            Color.White
        )
    }

    BackHandler() {
        navController.navigate(SecomiScreens.HomeScreen.name) {
            popUpTo(SecomiScreens.EventScreen.name) { inclusive = true }
        }
    }

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
    val configuration = LocalConfiguration.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_attendance_check),
                contentDescription = "출석체크 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.FillWidth
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "2022.05.23", fontWeight = FontWeight.Bold)
                Text(text = "2022.05.29", fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) {
                    Surface(
                        modifier = Modifier.size(70.dp),
                        shape = CircleShape,
                        color = BottomUnSelectedColor,
                        contentColor = Color.White
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "출석",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) {
                    Surface(
                        modifier = Modifier.size(70.dp),
                        shape = CircleShape,
                        color = BottomUnSelectedColor,
                        contentColor = Color.White
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "출석",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width((configuration.screenWidthDp * 0.9).dp)
                    .height(70.dp)
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LoginButtonColor,
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "출석 도장 찍기", fontWeight = FontWeight.SemiBold)
                    Text(text = "(일일 1회 출석)", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun QuizChallengeScaffold(navController: NavController) {
    val configuration = LocalConfiguration.current
    var inputValue by remember {
        mutableStateOf("")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.image_quiz),
                contentDescription = "Sample",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.FillWidth
            )
            Card(
                shape = RoundedCornerShape(10f),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(100.dp)
                    .padding(top = 10.dp),
            ) {
                BasicTextField(value = inputValue, onValueChange = { inputValue = it }) {
                    Box(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                        if (inputValue == "")
                            Text(
                                text = "100자 이내로 정답을 작성해주세요.",
                                color = PlaceholderColor
                            )
                        it()
                    }
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LoginButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "제출")
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                color = UnselectedButtonColor,
                thickness = 1.dp
            )
        }
    }
}