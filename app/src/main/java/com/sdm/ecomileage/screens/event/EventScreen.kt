package com.sdm.ecomileage.screens.event

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiBottomBar
import com.sdm.ecomileage.components.SecomiMainFloatingActionButton
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.event.currentEvent.response.AttendanceInfoResponse
import com.sdm.ecomileage.model.event.currentEvent.response.Attn
import com.sdm.ecomileage.model.event.currentEvent.response.Comment
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.isAutoLoginUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


@Composable
fun EventScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    eventViewModel: EventViewModel = hiltViewModel()
) {
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

    val context = LocalContext.current

    var isLoading by remember {
        mutableStateOf(false)
    }
    var isFail by remember {
        mutableStateOf(false)
    }

    val eventInfo = produceState<DataOrException<AttendanceInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = eventViewModel.getCurrentEventInfo()
    }.value

    if (eventInfo.loading == true) {
        isLoading = true
        Log.d("Event", "EventScreen: ${eventInfo.data}")
    } else if (eventInfo.data?.code != null) {
        if (eventInfo.data?.code == 200) {
            EventScaffold(eventInfo, navController)
            isLoading = false
        } else {
            Log.d("AUTO Login", "EventScreen: Fail data?")
            showShortToastMessage(context, "데이터를 정상적으로 받지 못하였습니다.")

            if (isAutoLoginUtil) {
                AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context,
                    screen = SecomiScreens.LoginScreen.name + "/0"
                )
            } else {
                navController.navigate(SecomiScreens.LoginScreen.name + "/0") {
                    popUpTo(SecomiScreens.EventScreen.name) { inclusive = true }
                }
            }
        }
    } else {
        isFail = true
    }

    if (isFail) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = LoginButtonColor)
        }
        Log.d("AUTO Login", "EventScreen: did you fail to get any data?")
        showShortToastMessage(context, "데이터를 받지 못하였습니다.")
        if (isAutoLoginUtil) {
            AutoLoginLogic(
                isLoading = { isLoading = true },
                navController = navController,
                context = context,
                screen = SecomiScreens.LoginScreen.name + "/0"
            )
        } else {
            navController.navigate(SecomiScreens.LoginScreen.name + "/0") {
                popUpTo(SecomiScreens.EventScreen.name) { inclusive = true }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun EventScaffold(
    eventInfo: DataOrException<AttendanceInfoResponse, Boolean, Exception>,
    navController: NavController
) {
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
                    EventAttendScaffold(eventInfo, navController)
                else if (pages[index] == "퀴즈 챌린지")
                    QuizChallengeScaffold(eventInfo.data!!.result.commentList, navController)
            }
        }
    }
}

@Composable
private fun EventAttendScaffold(
    eventInfo: DataOrException<AttendanceInfoResponse, Boolean, Exception>,
    navController: NavController,
    eventViewModel: EventViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current

    val localAttnList = SnapshotStateList<Attn>()
    eventInfo.data!!.result.attnList.forEach {
        localAttnList.add(it)
    }
    val currentWeek = SimpleDateFormat("u").format(System.currentTimeMillis()).toInt()
    var isClicked by remember { mutableStateOf(false) }

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

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) {
                    Box(contentAlignment = Alignment.Center) {
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

                        if (it == (currentWeek - 1) && isClicked)
                            DropDown {
                                Log.d("EventScreen", "EventAttendScaffold: $it ${currentWeek}")
                                Image(
                                    painter = painterResource(id = R.drawable.ic_attandance),
                                    contentDescription = "",
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                repeat(3) {
                    Box(contentAlignment = Alignment.Center) {
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
                        if (it == (currentWeek - 5) && isClicked)
                            DropDown {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_attandance),
                                    contentDescription = "",
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                    }

                }
            }
            Button(
                onClick = { isClicked = true },
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
private fun QuizChallengeScaffold(
    commentList: List<Comment>,
    navController: NavController
) {
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


@Composable
private fun DropDown(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(50)
        isOpen = true
    }

    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else 90f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}