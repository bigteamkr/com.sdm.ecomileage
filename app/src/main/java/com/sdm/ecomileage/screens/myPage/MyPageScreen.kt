package com.sdm.ecomileage.screens.myPage

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
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
import com.sdm.ecomileage.data.ChallengeList
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.myPage.userFeedInfo.response.UserFeedInfoResponse
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.home.HomeViewModel
import com.sdm.ecomileage.screens.homeDetail.HomeDetailViewModel
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.UserReportOptions
import com.sdm.ecomileage.utils.currentUUIDUtil
import com.sdm.ecomileage.utils.currentLoginedUserId
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

    val myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>
    val userFeedInfo: DataOrException<UserFeedInfoResponse, Boolean, Exception>

    if (userId == "myPage" || userId == currentLoginedUserId) {
        myFeedInfo = produceState<DataOrException<MyFeedInfoResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = myPageViewModel.getMyFeedInfo()
        }.value

        if (myFeedInfo.loading == true)
            CircularProgressIndicator()
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
            PageScaffold(navController, userId, myFeedInfo)
        }

    } else {
        userFeedInfo = produceState<DataOrException<UserFeedInfoResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = myPageViewModel.getUserFeedInfo(userId!!)
        }.value

        if (userFeedInfo.loading == true)
            CircularProgressIndicator()
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
            PageScaffold(navController, userId!!, userFeedInfo = userFeedInfo)
        }
    }
}

@Composable
private fun PageScaffold(
    navController: NavController,
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
        floatingActionButtonPosition = FabPosition.Center
    ) {
        if (userId == "myPage" || userId == currentLoginedUserId)
            MyPageLayout(navController, myFeedInfo)
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
    myFeedInfo: DataOrException<MyFeedInfoResponse, Boolean, Exception>?
) {
    var selectedButton by remember {
        mutableStateOf("내 게시물")
    }

    Column(
        modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
    ) {

        MyPageFilterButton(selectedButton) {
            selectedButton = it
        }

        if (selectedButton == "내 게시물") {
            MyFeedLayout(navController, myFeedInfo!!)
        }
        if (selectedButton == "내 챌린지") {
            MyChallengeLayout()
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
    Column {
        Text(
            text = "오늘",
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
            color = IndicationColor
        )
        Divider()
        MyFeedList(navController, myFeedInfo)
    }
}


@Composable
private fun MyChallengeLayout() {
    Column() {
        Text(
            text = "항목 당 일일 1회씩만 도전 가능합니다.",
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
            color = IndicationColor
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 70.dp)
        ) {
            items(ChallengeList) { photo ->
                var text = when (photo) {
                    R.drawable.image_empty_dish -> "빈그릇 챌린지"
                    R.drawable.image_public_transport -> "대중교통 챌린지"
                    R.drawable.image_thermos -> "개인 텀블러 챌린지"
                    R.drawable.image_label_detach -> "라벨지 떼기 챌린지"
                    R.drawable.image_basket -> "장바구니 챌린지"
                    R.drawable.image_pull_a_plug -> "코드뽑기 챌린지"
                    R.drawable.image_empty_bottle -> "용기내 챌린지"
                    R.drawable.image_upcycling -> "업사이클링 챌린지"
                    else -> "챌린지"
                }
                val pointString = AnnotatedString(
                    "5 ",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                val epString = AnnotatedString(
                    "EP",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .clickable { },
                    contentAlignment = Alignment.BottomStart
                ) {
                    Image(
                        painter = painterResource(id = photo),
                        contentDescription = "",
                        modifier =
                        Modifier
                            .fillMaxSize()
                    )
                    Column(
                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = text,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = pointString + epString,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MyPageFilterButton(
    selectedButton: String,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onClick("내 게시물")
            },
            modifier = Modifier
                .width(175.dp)
                .height(35.dp)
                .padding(start = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == "내 게시물") PointColor else UnselectedButtonColor)
        ) {
            Text(
                text = "내 게시물",
                color = if (selectedButton == "내 게시물") Color.White else Color.Black,
                fontWeight = if (selectedButton == "내 게시물") FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                onClick("내 챌린지")
            },
            modifier = Modifier
                .width(175.dp)
                .height(35.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == "내 챌린지") PointColor else UnselectedButtonColor)
        ) {
            Text(
                text = "내 챌린지",
                color = if (selectedButton == "내 챌린지") Color.White else Color.Black,
                fontWeight = if (selectedButton == "내 챌린지") FontWeight.SemiBold else FontWeight.Normal,
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
                Brush.verticalGradient(TopBarColor)
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )

                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "setting",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navController.navigate(SecomiScreens.SettingsScreen.name)
                            },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_push_on),
                        contentDescription = "push on",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navController.navigate(SecomiScreens.NoticeScreen.name) {
                                    popUpTo(SecomiScreens.MyPageScreen.name) { inclusive = true }
                                }
                            }
                    )
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
    homeDetailViewModel: HomeDetailViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(myFeedInfo.data!!.result.feedList) { index, data ->
            var likeYN by remember {
                mutableStateOf(data.likeyn)
            }
            var isShowingFeedPopUp by remember {
                mutableStateOf(false)
            }

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
                reportDialogCallAction = {},
                otherIcons = mapOf(
                    "comment" to R.drawable.ic_comment,
                    "empty" to 70,
                    "more" to R.drawable.ic_more
                ),
                likeYN = likeYN,
                likeCount = data.likeCount,
                commentCount = data.commentCount.toString()
            )

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
                            R.drawable.ic_like_off,
                            R.drawable.ic_like_on
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
                            "comment" to R.drawable.ic_comment,
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

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
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
                currentScreen = SecomiScreens.MyPageScreen.name,
                contentImageList = data.imageList,
                onReactionClick = {
                    likeYN = it
                    scope.launch {
                        homeViewModel.postFeedLike(data.feedsno, !likeYN).let {
                            Log.d("myPage myList postLike", "MyFeedCard: ${it.data?.message}")
                        }
                    }
                },
                reportDialogCallAction = {},
                otherIcons = mapOf(
                    "comment" to R.drawable.ic_comment,
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
                            R.drawable.ic_like_off,
                            R.drawable.ic_like_on
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
                            "comment" to R.drawable.ic_comment,
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

    Card(
        modifier = Modifier
            .padding(5.dp)
            .padding(vertical = 5.dp)
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
                    .height(heightModifier)
            ) {
                CardImageRow(
                    contentImageList,
                    currentScreen,
                    showIndicator = false
                )
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                CustomReaction(
                    modifier = Modifier.size(18.dp),
                    textPadding = 30.dp,
                    iconResourceList = listOf(
                        R.drawable.ic_like_off,
                        R.drawable.ic_like_on
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

                otherIcons?.forEach { (key, icon) ->
                    when (key) {
                        "comment" -> {
                            Icon(
                                painter = painterResource(icon),
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
                            Text(
                                text = commentCount,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 5.dp),
                                style = MaterialTheme.typography.subtitle2,
                                color = CardIconsColor
                            )
                        }
                        "empty" -> {
                            Spacer(modifier = Modifier.width(icon.dp))
                        }
                        "more" -> {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = "설정창 열기",
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .size(18.dp)
                                    .clickable {
                                        if (reportDialogCallAction != null)
                                            reportDialogCallAction(true)
                                    },
                                tint = CardIconsColor
                            )
                        }
                    }
                }
            }
        }
    }
}