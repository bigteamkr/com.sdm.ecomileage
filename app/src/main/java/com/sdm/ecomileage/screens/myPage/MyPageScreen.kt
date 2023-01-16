package com.sdm.ecomileage.screens.myPage

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.components.appBarComponents.MoreVertComponent
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import com.sdm.ecomileage.model.myPage.userHistoryInfo.response.UserHistoryResponse
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.home.HomeViewModel
import com.sdm.ecomileage.screens.homeDetail.HomeDetailViewModel
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.UserReportOptions
import com.sdm.ecomileage.utils.currentLoginedUserId
import com.sdm.ecomileage.utils.currentUUIDUtil
import com.sdm.ecomileage.utils.noChangeMileageAlarm
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    userId: String?,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    SideEffect {
        systemUiController.setStatusBarColor(StatusBarGreenColor)
    }

    BackHandler {
        navController.navigate(SecomiScreens.HomeScreen.name) {
            popUpTo(SecomiScreens.MyPageScreen.name) { inclusive = true }
        }
    }

    var isFail by remember {
        mutableStateOf(false)
    }
    val myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>
    val userFeedInfo: DataOrException<UserFeedInfoResponse, Boolean, Exception>

    if (userId == "myPage" || userId == currentLoginedUserId) {
        myFeedInfo = produceState<DataOrException<MyFeedInfoResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = myPageViewModel.getMyFeedInfo()
        }.value

        if (myFeedInfo.loading == true)
            CircularProgressIndicator(color = LoginButtonColor)
        else if (myFeedInfo.data?.result != null) {
            myFeedInfo.data?.code?.let {
                if (it == 200) return@let
                else AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context,
                    screen = SecomiScreens.MyPageScreen.name
                )
            }
            PageScaffold(navController, systemUiController, userId, myFeedInfo)
        } else {
            isFail = true
        }

    } else {
        userFeedInfo = produceState<DataOrException<UserFeedInfoResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = myPageViewModel.getUserFeedInfo(userId!!)
        }.value

        if (userFeedInfo.loading == true)
            CircularProgressIndicator(color = LoginButtonColor)
        else if (userFeedInfo.data?.result != null) {
            userFeedInfo.data?.code?.let {
                if (it == 200) return@let
                else AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context,
                    screen = SecomiScreens.MyPageScreen.name
                )
            }
            PageScaffold(navController, systemUiController, userId!!, userFeedInfo = userFeedInfo)
        } else {
            isFail = true
        }
    }

    if (isFail) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = LoginButtonColor)
        }
        showShortToastMessage(context, "데이터를 정상적으로 받지 못하였습니다.").let {
            navController.popBackStack()
        }
    }
}

@Composable
private fun PageScaffold(
    navController: NavController,
    systemUiController: SystemUiController,
    userId: String,
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>? = null,
    userFeedInfo: DataOrException<UserFeedInfoResponse, Boolean, Exception>? = null,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isShowingReportDialog by remember {
        mutableStateOf(false)
    }
    var isReported by remember {
        mutableStateOf(false)
    }
    if (userFeedInfo != null) isReported = userFeedInfo.data?.result?.reportuseryn ?: false

    var isShowingBlockDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            if (myFeedInfo != null) MyPageTopBar(
                navController,
                myFeedInfo
            ) else if (userFeedInfo != null) SecomiTopAppBar(
                title = "",
                currentScreen = SecomiScreens.MyPageScreen.name,
                navController = navController,
                backgroundColor = TopBarColorOrigin,
                navigationIcon = painterResource(id = R.drawable.ic_back_arrow),
                actionIconsList = listOf {
                    MoreVertComponent(
                        navController = navController, options = listOf(
                            {
                                DropdownMenuItem(onClick = { isShowingReportDialog = true }) {
                                    Text(text = "계정 신고하기")
                                    Log.d("Report", "MyPageScaffold: Report why?")
                                }
                            },
                            {
                                DropdownMenuItem(onClick = { isShowingReportDialog = true }) {
                                    Text(text = "계정 차단하기")
                                    Log.d("Report", "MyPageScaffold: Block why?")
                                }
                            },
                        )
                    )
                }
            )
        },
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.MyPageScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = BasicBackgroundColor
    ) {
        if (userId == "myPage" || userId == currentLoginedUserId)
            MyPageLayout(navController, systemUiController, myFeedInfo)
        else
            UserFeedLayout(navController, userFeedInfo!!, isReported)
    }

    if (isShowingReportDialog || isShowingBlockDialog) {
        CustomReportDialog(
            title = "${userFeedInfo?.data?.result?.username}님을 신고하기",
            reportAction = { selectedOptionCode, reportDetailDescription ->
                if (selectedOptionCode == "00") showShortToastMessage(context, "신고 사유를 선택해주세요.")

                scope.launch {
                    if (isShowingReportDialog) {
                        myPageViewModel.postNewUserReport(
                            userId,
                            selectedOptionCode,
                            reportDetailDescription.toString(),
                            !userFeedInfo?.data!!.result.feedList[0].reportuseryn
                        )
                        isReported = true
                    }

                }
                isShowingReportDialog = false
            },
            dismissAction = { isShowingReportDialog = false },
            reportOptions = UserReportOptions
        )
    }
}

@Composable
private fun MyPageLayout(
    navController: NavController,
    systemUiController: SystemUiController,
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>?
) {
    var selectedButton by remember {
        mutableStateOf("내 게시물")
    }

    Column(
        modifier = Modifier.padding(top = 5.dp)
    ) {

        MyPageFilterButton(selectedButton) {
            selectedButton = it
        }

        if (selectedButton == "내 게시물") {
            MyFeedLayout(navController, myFeedInfo!!)
        }
        if (selectedButton == "내 활동") {
            MyWorkLayout(navController, systemUiController)
        }
    }
}

@Composable
private fun MyWorkLayout(
    navController: NavController,
    systemUiController: SystemUiController,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(false) }
    var isFail by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val tintColor = IconTintColor

    val spacing = with(LocalDensity.current) {
        (25.sp.toDp()) / 2
    }

    val myHistoryInfo: DataOrException<UserHistoryResponse, Boolean, Exception>

    myHistoryInfo = produceState<DataOrException<UserHistoryResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = myPageViewModel.getUserHistoryInfo()
    }.value

    if (myHistoryInfo.loading == true)
        CircularProgressIndicator(color = LoginButtonColor)
    else if (myHistoryInfo.data?.result != null) {
        myHistoryInfo.data?.code?.let {
            if (it == 200) return@let
            else AutoLoginLogic(
                isLoading = { isLoading = true },
                navController = navController,
                context = context,
                screen = SecomiScreens.MyPageScreen.name
            )
        }
        myWorkContent(myHistoryInfo, navController, systemUiController)
    } else {
        isFail = true
    }

}

@Composable
private fun myWorkContent(
    myHistoryInfo: DataOrException<UserHistoryResponse, Boolean, Exception>,
    navController: NavController,
    systemUiController: SystemUiController
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val tintColor = IconTintColor

    var noCandidateDialog by remember { mutableStateOf(false) }

    val spacing = with(LocalDensity.current) {
        (25.sp.toDp()) / 2
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyRow() {
                item {
                    visitInfo(
                        spacing = spacing,
                        visitCount = myHistoryInfo.data!!.result.visits.toString()
                    )
                }
                item {
                    mileageInfo(
                        spacing = spacing,
                        mileageCount = myHistoryInfo.data!!.result.point.toString()
                    )
                }
//                item { writeInfo() }
//                item { eduInfo(radius = 31.dp) }
            }

            Spacer(modifier = Modifier.height(20.dp))

            mileageHistoryCard(context, myHistoryInfo, navController, systemUiController)
        }

        // 마일리지 전환하기 버튼
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                    // 마일리지 5000점 이하일 경우 '마일리지 전환 기능 비활성화'
                    if (myHistoryInfo.data!!.result.point < 5000) {
                        noCandidateDialog = true
                        // 추후 삭제 (테스트 기간 동안만 활성)
                        // navController.navigate(SecomiScreens.MileageChangeScreen.name)
                    }
                    else
                    // 마일리지 전환신청 사용자 정보 입력화면
                    {   navController.navigate(SecomiScreens.MileageChangeScreen.name) {
                        popUpTo(SecomiScreens.MyPageScreen.name) { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = IconTintColor),
            shape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20),
            elevation = ButtonDefaults.elevation(11.dp),
            contentPadding = PaddingValues(bottom = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "마일리지 전환하기",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "마일리지 전환하기 화살표 아이콘",
                    tint = Color.White
                )
            }
        }

        if (noCandidateDialog) {
            Dialog(onDismissRequest = { noCandidateDialog = false }) {
                Surface(
                    modifier = Modifier
                        .width((configuration.screenWidthDp * 0.75).dp)
                        .height((configuration.screenHeightDp * 0.25).dp),
                    shape = RoundedCornerShape(5)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            // alert 타이틀
                            text = "전환대상이 아닙니다.",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 17.sp
                        )
                        Text(
                            // 마일리지 전환대상 아님 알람 표출
                            text = noChangeMileageAlarm,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 15.sp
                        )
                        Button(
                            onClick = { noCandidateDialog = false },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = NavGreyColor)
                        ) {
                            Text(
                                text = "닫기",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// 마일리지 사용내역
@Composable
private fun mileageHistoryCard(
    context: Context,
    myHistoryInfo: DataOrException<UserHistoryResponse, Boolean, Exception>,
    navController: NavController,
    systemUiController: SystemUiController
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 11.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "마일리지 내역",
                    color = CardContentColor,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    fontSize = 20.sp,
                    maxLines = 1
                )

                Text(
                    text = "전체보기",
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(SecomiScreens.MyHistoryScreen.name + "/${myHistoryInfo.data!!.result.point}")
                        },
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            // 마일리지 적립 / 사용 히스토리
            LazyColumn {
                itemsIndexed(myHistoryInfo.data!!.result.mileList) { index, item ->
                    if (index <= 2) {
                        mileageHistoryItem(
                            tintColor = if (item.miletype == "U") UsedMileageColor else IconTintColor,
                            history = item.milereason,
                            period = item.miledate,
                            type = item.miletype != "U",
                            point = item.mileagepoint
                        )
                    }
                }

                // data = DataOrException의 data. 마일리지 적립내역이 없을 경우가 아닌 경우
                if (myHistoryInfo.data!!.result.mileList.isEmpty()) {
                    item {
                        Surface(
                            modifier = Modifier.padding(vertical = 30.dp)
                        ) {
                            Text(
                                text = "내역이 없습니다.",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.SansSerif,
                                letterSpacing = 1.0.sp,
                                color = CardContentColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun mileageHistoryItem(
    tintColor: Color,
    history: String,
    period: String,
    type: Boolean,
    point: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mileage), contentDescription = "",
                tint = tintColor
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = history,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.0.sp,
                    color = CardContentColor
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = period,
                    fontSize = 12.sp,
                    color = PlaceholderColor,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.0.sp
                )
            }
        }

        Text(
            text = (if (type) "+" else "-") + point,
            modifier = Modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = 1.0.sp,
            color = CardContentColor
        )
    }
}

@Composable
private fun writeInfo(
    tintColor: Color = IconTintColor
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .padding(start = 7.5.dp, top = 15.dp, bottom = 15.dp, end = 7.5.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 12.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier.size(27.dp),
                    tint = tintColor
                )
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "223",
                        color = CardContentColor,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.2.sp,
                        fontSize = 24.sp,
                        maxLines = 1
                    )
                    Text(
                        text = "게시글",
                        color = PlaceholderColor,
                        letterSpacing = 0.75.sp,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "1000",
                        color = CardContentColor,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.2.sp,
                        fontSize = 24.sp,
                        maxLines = 1
                    )
                    Text(
                        text = "댓글",
                        color = PlaceholderColor,
                        letterSpacing = 0.75.sp,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "글 작성기록",
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun mileageInfo(
    tintColor: Color = IconTintColor,
    spacing: Dp,
    mileageCount: String
) {
    Card(
        modifier = Modifier
            .width(162.5.dp)
            .padding(vertical = 15.dp)
            .padding(horizontal = 7.5.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 12.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mileage),
                    contentDescription = "",
                    modifier = Modifier.size(27.dp),
                    tint = tintColor
                )
            }

            Spacer(modifier = Modifier.height(spacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 15.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = mileageCount,
                    color = CardContentColor,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(spacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "마일리지",
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun visitInfo(
    tintColor: Color = IconTintColor,
    spacing: Dp,
    visitCount: String
) {

    Card(
        modifier = Modifier
            .width(170.dp)
            .padding(vertical = 15.dp)
            .padding(start = 15.dp, end = 7.5.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 12.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "",
                    modifier = Modifier.size(27.dp),
                    tint = tintColor
                )
            }

            Spacer(modifier = Modifier.height(spacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = visitCount,
                    color = CardContentColor,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp,
                    fontSize = 28.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(10.dp))
//                Text(
//                    text = "일",
//                    color = PlaceholderColor,
//                    letterSpacing = 0.75.sp,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Normal
//                )
            }

            Spacer(modifier = Modifier.height(spacing))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "방문일",
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun eduInfo(
    tintColor: Color = IconTintColor,
    radius: Dp
) {

    Card(
        modifier = Modifier
            .width(170.dp)
            .padding(vertical = 15.dp)
            .padding(start = 7.5.dp, end = 15.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 12.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_education),
                    contentDescription = "",
                    modifier = Modifier.size(27.dp),
                    tint = tintColor
                )
            }

            CircularProgressBar(0.7f, radius = radius)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "교육 시청률",
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}


@Composable
fun CircularProgressBar(
    percentage: Float,
    fontSize: TextUnit = 20.sp,
    radius: Dp = 50.dp,
    color: Color = IconTintColor,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(start = 20.dp)
            .size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawCircle(
                color = BottomSheetDividerColor,
                radius = radius.toPx(),
                style = Stroke(strokeWidth.toPx())
            )

            drawArc(
                color = color,
                -270f,
                360f * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (curPercentage.value * 100).toInt().toString() + "%",
                modifier = Modifier.padding(5.dp),
                color = CardContentColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp,
                maxLines = 1
            )
            if (curPercentage.value == 1.toFloat()) {
                Text(
                    text = "%",
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    color = CardContentColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun UserFeedLayout(
    navController: NavController,
    userFeedInfo: DataOrException<UserFeedInfoResponse, Boolean, Exception>,
    isReported: Boolean,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var isFollow by remember {
        mutableStateOf(userFeedInfo.data!!.result.followyn)
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(
                userId = userFeedInfo.data!!.result.userid,
                image = userFeedInfo.data!!.result.feedList[0].profileimg,
                modifier = Modifier.size(80.dp),
                navController = navController,
                isNonClickable = true
            )
            ProfileName(
                name = userFeedInfo.data!!.result.username,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    isFollow = !isFollow
                    scope.launch {
                        myPageViewModel.putNewFollowInfo(
                            currentUUIDUtil,
                            userFeedInfo.data!!.result.userid,
                            isFollow
                        )
                    }
                },
                enabled = !isReported,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isFollow) LoginButtonColor else PlaceholderColor,
                    disabledBackgroundColor = ReportedButtonColor
                )
            ) {
                Text(
                    text = if (isReported) "신고됨" else if (isFollow) "팔로우 중" else "팔로우하기",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(
            text = "오늘",
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
            color = IndicationColor
        )
        Divider()
        if (!isReported) UserFeedList(navController, userFeedInfo)
    }
}

@Composable
private fun MyFeedLayout(
    navController: NavController,
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>
) {
    Column(modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp)) {
        Text(
            text = "오늘",
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
            color = IndicationColor
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp)
        )
        MyFeedList(navController, myFeedInfo)
    }
}

@Composable
private fun MyPageFilterButton(
    selectedButton: String,
    onClick: (String) -> Unit
) {
    val firstButtonName = "내 게시물"
    val secondButtonName = "내 활동"


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onClick(firstButtonName)
            },
            modifier = Modifier
                .width(165.dp)
                .height(35.dp)
                .padding(start = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == firstButtonName) PointColor else UnselectedButtonColor),
            elevation = ButtonDefaults.elevation(12.dp)
        ) {
            Text(
                text = firstButtonName,
                color = if (selectedButton == firstButtonName) Color.White else Color.Black,
                fontWeight = if (selectedButton == firstButtonName) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                onClick(secondButtonName)
            },
            modifier = Modifier
                .width(155.dp)
                .height(35.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == secondButtonName) PointColor else UnselectedButtonColor),
            elevation = ButtonDefaults.elevation(12.dp)
        ) {
            Text(
                text = secondButtonName,
                color = if (selectedButton == secondButtonName) Color.White else Color.Black,
                fontWeight = if (selectedButton == secondButtonName) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MyPageTopBar(
    navController: NavController,
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>
) {
    val interactionSource = remember { MutableInteractionSource() }

    val pointString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 21.sp
            )
        ) {
            append("${myFeedInfo.data!!.result.point} ")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 17.sp
            )
        ) {
            append("EP")
        }
    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(
                Brush.verticalGradient(TopBarColorOrigin)
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                // 2023.01.16 '설정' 버튼 오른쪽 상단으로 이동 조치, 알림 버튼 미사용 블럭
                horizontalArrangement = Arrangement.End
            ) {
                /* 2023.01.16 뒤로가기 버튼 블럭처리 (원인 : logout 처리)
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { navController.popBackStack() },
                    tint = Color.White
                )
                */

                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "setting",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                navController.navigate(SecomiScreens.SettingsScreen.name) {
                                    popUpTo(SecomiScreens.MyPageScreen.name) { inclusive = true }
                                }
                            },
                        tint = Color.White
                    )

                    /* 2023.01.16 notice icon 제외 (기능 없음)
                    Spacer(modifier = Modifier.width(9.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_push_off),
                        contentDescription = "push off",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
//                                navController.navigate(SecomiScreens.NoticeScreen.name) {
//                                    popUpTo(SecomiScreens.MyPageScreen.name) { inclusive = true }
//                                }
                            }
                    )*/
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileImage(
                        userId = myFeedInfo.data!!.result.userid,
                        image = myFeedInfo.data!!.result.profileimg,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 2.dp)
                            .size(25.dp),
                        navController = navController
                    )
                    Text(
                        text = myFeedInfo.data!!.result.username,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = " 님의 에코 마일리지",
                        modifier = Modifier.padding(top = 2.dp),
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Start
                    )
                }
                Text(
                    pointString, Modifier.padding(end = 15.dp),
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyFeedList(
    navController: NavController,
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    var deleteFeedNo: Int? = null
    var expanded by remember {
        mutableStateOf(false)
    }
    var deleteNotice by remember {
        mutableStateOf(false)
    }
    val deletedArticle = remember {
        SnapshotStateList<Int>()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp)
    ) {
        itemsIndexed(myFeedInfo.data!!.result.feedList) { index, data ->
            var likeYN by remember {
                mutableStateOf(data.likeyn)
            }
            var isShowingFeedPopUp by remember {
                mutableStateOf(false)
            }

            AnimatedVisibility(
                visible = !deletedArticle.contains(data.feedsno),
                enter = EnterTransition.None,
                exit = fadeOut(tween(500))
            ) {

                Box {
                    MyFeedCard(
                        navController = navController,
                        feedNo = data.feedsno,
                        currentScreen = SecomiScreens.MyPageScreen.name,
                        contentImageList = data.imageList,
                        onClick = {
                            isShowingFeedPopUp = true
                        },
                        onReactionClick = {
                            likeYN = it
                            scope.launch {
                                homeViewModel.postFeedLike(data.feedsno, likeYN).let {
                                    Log.d(
                                        "myPage myList postLike",
                                        "MyFeedCard: ${it.data?.message}"
                                    )
                                }
                            }
                        },
                        reportDialogCallAction = {
                            deleteFeedNo = data.feedsno
                            deleteNotice = true
                        },
                        otherIcons = mapOf(
                            "comment" to R.drawable.ic_new_comment,
                            "more" to R.drawable.ic_more
                        ),
                        likeYN = likeYN,
                        likeCount = data.likeCount,
                        commentCount = data.commentCount.toString()
                    )
                }
            }


            if (index == myFeedInfo.data!!.result.feedList.lastIndex)
                Spacer(modifier = Modifier.height(230.dp))
            if (isShowingFeedPopUp) {
                Dialog(
                    onDismissRequest = { isShowingFeedPopUp = false },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    MainCardFeed(
                        contentImageList = data.imageList,
                        profileImage = data.profileimg,
                        profileId = data.userid,
                        profileName = data.userName,
                        reactionIcon = listOf(
                            R.drawable.ic_new_like_off,
                            R.drawable.ic_new_like_on
                        ),
                        reactionData = data.likeCount,
                        onReactionClick = {
                            scope.launch {
                                homeViewModel.postFeedLike(data.feedsno, likeYN).let {
                                    Log.d(
                                        "myPage myList postLike",
                                        "MyFeedCard: ${it.data?.message}"
                                    )
                                }
                            }
                        },
                        reactionTint = LikeColor,
                        likeYN = data.likeyn,
                        colorIcon = null,
                        otherIcons = mapOf(
                            "comment" to R.drawable.ic_new_comment,
                            "more" to R.drawable.ic_new_burger
                        ),
                        navController = navController,
                        reportDialogCallAction = {
                            deleteFeedNo = data.feedsno
                            deleteNotice = true
                        },
                        currentScreen = SecomiScreens.MyPageScreen.name,
                        feedNo = data.feedsno,
                        contentText = data.feedcontent,
                        hashtagList = data.hashtags,
                        destinationScreen = null,
                        showIndicator = true,
                        sizeModifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.65f)
                    )
                }
            }

            if (deleteNotice) {
                Dialog(onDismissRequest = {
                    deleteNotice = false
                    deleteFeedNo = null
                }) {
                    Surface(
                        modifier = Modifier
                            .width((configuration.screenWidthDp * 0.5).dp)
                            .height((configuration.screenHeightDp * 0.25).dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "정말로\n삭제하시겠습니까?")
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        if (deleteFeedNo != null)
                                            scope.launch { myPageViewModel.deleteMyFeed(feedNo = deleteFeedNo!!) }
                                        deletedArticle.add(deleteFeedNo!!)
                                        deleteNotice = false
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = LoginButtonColor)
                                ) {
                                    Text(
                                        text = "네",
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                Button(
                                    onClick = { deleteNotice = false },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = UnableUploadButtonColor)
                                ) {
                                    Text(
                                        text = "아니오",
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserFeedList(
    navController: NavController,
    userFeedInfo: DataOrException<UserFeedInfoResponse, Boolean, Exception>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    homeDetailViewModel: HomeDetailViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var isShowingReporting by remember {
        mutableStateOf(false)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp)
    ) {
        itemsIndexed(userFeedInfo.data!!.result.feedList) { index, data ->
            var likeYN by remember {
                mutableStateOf(data.likeyn)
            }
            var isShowingFeedPopUp by remember {
                mutableStateOf(false)
            }

            MyFeedCard(
                navController = navController,
                feedNo = data.feedsno,
                onClick = { isShowingFeedPopUp = true },
                currentScreen = SecomiScreens.MyPageScreen.name + "/UserFeedList",
                contentImageList = data.imageList,
                onReactionClick = {
                    likeYN = it
                    scope.launch {
                        homeViewModel.postFeedLike(data.feedsno, !likeYN).let {
                            Log.d("myPage myList postLike", "MyFeedCard: ${it.data?.message}")
                        }
                    }
                },
                reportDialogCallAction = {
                    isShowingReporting = true
                },
                otherIcons = mapOf(
                    "comment" to R.drawable.ic_new_comment,
                    "empty" to 55,
                    "more" to R.drawable.ic_more
                ),
                likeYN = likeYN,
                likeCount = data.likeCount,
                commentCount = data.commentCount.toString()
            )

            if (index == userFeedInfo.data!!.result.feedList.lastIndex)
                Spacer(modifier = Modifier.height(230.dp))

            if (isShowingFeedPopUp) {
                Dialog(
                    onDismissRequest = { isShowingFeedPopUp = false },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    MainCardFeed(
                        contentImageList = data.imageList,
                        profileImage = data.profileimg,
                        profileId = data.userid,
                        profileName = data.userName,
                        reactionIcon = listOf(
                            R.drawable.ic_new_like_off,
                            R.drawable.ic_new_like_on
                        ),
                        reactionData = data.likeCount,
                        onReactionClick = {
                            scope.launch {
                                homeViewModel.postFeedLike(data.feedsno, !likeYN).let {
                                    Log.d(
                                        "myPage myList postLike",
                                        "MyFeedCard: ${it.data?.message}"
                                    )
                                }
                            }
                        },
                        reactionTint = LikeColor,
                        likeYN = data.likeyn,
                        colorIcon = null,
                        otherIcons = mapOf(
                            "comment" to R.drawable.ic_new_comment,
                            "more" to R.drawable.ic_more
                        ),
                        navController = navController,
                        reportDialogCallAction = {},
                        currentScreen = SecomiScreens.MyPageScreen.name,
                        feedNo = data.feedsno,
                        contentText = data.feedcontent,
                        hashtagList = data.hashtags,
                        destinationScreen = null,
                        showIndicator = true,
                        sizeModifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.65f)
                    )
                }
            }

            if (isShowingReporting) {
                CustomReportDialog(
                    title = "${data.userName}님의 게시글 신고하기",
                    reportAction = { selectedOptionCode, reportDetailDescription ->

                    },
                    dismissAction = {
                        isShowingReporting = false
                    },
                    reportOptions = UserReportOptions
                )
            }
        }
    }
}


@Composable
fun MyFeedCard(
    navController: NavController,
    feedNo: Int,
    onClick: () -> Unit,
    heightModifier: Dp = 120.dp,
    currentScreen: String,
    contentImageList: List<String>,
    onReactionClick: ((Boolean) -> Unit),
    otherIcons: (Map<String, Int>)?,
    likeYN: Boolean,
    likeCount: Int,
    commentCount: String,
    reportDialogCallAction: ((Boolean) -> Unit)?,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(heightModifier)
            ) {
                CardImageRow(
                    contentImageList,
                    currentScreen,
                    showIndicator = false,
                    radius = 6
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomReaction(
                        modifier = Modifier.size(18.dp),
                        textPadding = 30.dp,
                        iconResourceList = listOf(
                            R.drawable.ic_new_like_off,
                            R.drawable.ic_new_like_on
                        ),
                        reactionData = likeCount,
                        onClickReaction = {
                            onReactionClick(!likeYN)
                            scope.launch {
                                homeViewModel.postFeedLike(feedsNo = feedNo, !likeYN)
                            }
                        },
                        tintColor = LikeColor,
                        likeYN = likeYN
                    )

                    if (otherIcons?.get("comment") != null) {
                        Text(
                            text = commentCount,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 5.dp),
                            style = MaterialTheme.typography.subtitle2,
                            color = CardIconsColor
                        )

                        Icon(
                            painter = painterResource(otherIcons["comment"]!!),
                            contentDescription = "댓글창으로 이동하기",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(18.dp)
                                .clickable {
                                    navController.navigate(SecomiScreens.HomeDetailScreen.name + "/$feedNo") {
                                        launchSingleTop
                                        popUpTo(currentScreen)
                                    }
                                },
                            tint = CardIconsColor
                        )
                    }

                }

                otherIcons?.forEach { (key, icon) ->
                    when (key) {
                        "more" -> {
                            Box {
                                Icon(
                                    painter = painterResource(icon),
                                    contentDescription = "설정창 열기",
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable {
                                            if (reportDialogCallAction != null && currentScreen == SecomiScreens.MyPageScreen.name + "/UserFeedList")
                                                reportDialogCallAction(true)
                                            else
                                                expanded = true
                                        },
                                    tint = CardIconsColor
                                )

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    DropdownMenuItem(onClick = {
                                        reportDialogCallAction!!(true)
                                        expanded = false
                                    }) {
                                        Text(text = "삭제하기")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}