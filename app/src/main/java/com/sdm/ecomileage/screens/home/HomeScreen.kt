package com.sdm.ecomileage.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.components.appBarComponents.AlarmComponent
import com.sdm.ecomileage.components.appBarComponents.RankingComponent
import com.sdm.ecomileage.components.appBarComponents.SearchComponent
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.homeInfo.response.Friend
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.LikeColor
import com.sdm.ecomileage.ui.theme.LoginButtonColor
import com.sdm.ecomileage.ui.theme.StatusBarGreenColor
import com.sdm.ecomileage.ui.theme.TopBarColor
import com.sdm.ecomileage.utils.MainFeedReportOptions
import com.sdm.ecomileage.utils.doubleBackForFinish
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    BackHandler {
        doubleBackForFinish(context)
    }

    val homeInfo = produceState<DataOrException<HomeInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getHomeInfo()
    }.value




    if (homeInfo.loading == true)
        CircularProgressIndicator(color = LoginButtonColor)
    else if (homeInfo.data?.result != null) {
        homeInfo.data?.code?.let {
            if (it == 200) return@let
            else {
                AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context,
                    screen = SecomiScreens.HomeScreen.name
                )
            }
        }
        HomeScaffold(navController, homeInfo)
    }

    if (isLoading)
        CircularProgressIndicator(color = LoginButtonColor)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun HomeScaffold(
    navController: NavController,
    homeInfoResponse: DataOrException<HomeInfoResponse, Boolean, Exception>
) {
    var pushIcon by remember {
        mutableStateOf(R.drawable.ic_push_off)
    }
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = homeInfoResponse.data!!.result.userDept,
                navController = navController,
                currentScreen = SecomiScreens.HomeScreen.name,
                backgroundColor = TopBarColor,
                actionIconsList = listOf(
                    {
                        SearchComponent(
                            navController = navController,
                            currentScreens = SecomiScreens.HomeScreen.name
                        )
                    },
                    {
                        RankingComponent(
                            navController = navController,
                            currentScreens = SecomiScreens.HomeScreen.name
                        )
                    },
                    {
                        AlarmComponent(
                            navController = navController,
                            currentScreens = SecomiScreens.HomeScreen.name
                        )
                    }
                ),
            )
        },
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.HomeScreen.name,
                scrollState = scrollState
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column {
            HomeUserFeedRow(navController, homeInfoResponse.data!!.result.friendList)
            HomeMainContent(navController, scrollState)
        }
    }
}

@Composable
private fun HomeMainContent(
    navController: NavController,
    scrollState: LazyListState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var scope = rememberCoroutineScope()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val pagingData = homeViewModel.pager.collectAsLazyPagingItems()


    var dropDownOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }

    var reportDialogVisible by remember {
        mutableStateOf(false)
    }

    var reportTargetFeedNo by remember {
        mutableStateOf<Int?>(null)
    }

    var reportedTargetName by remember {
        mutableStateOf("")
    }

    var isCurrentFeedReporting by remember {
        mutableStateOf(false)
    }


    var reportListValue: String?
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch {
                homeViewModel.refresh {
                    pagingData.refresh().let { homeViewModel.invalidateDataSource() }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 75.dp),
            state = scrollState
        ) {
            pagingData.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {}
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .align(Alignment.Center), color = LoginButtonColor
                                )
                            }
                        }
                    }
                    loadState.prepend is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .align(Alignment.Center), color = LoginButtonColor
                                )
                            }
                        }
                    }
                    loadState.refresh is LoadState.Loading -> {
                        visible = false
                    }
                    else -> {
                        visible = true
                    }
                }
            }

            items(pagingData) { data ->
                data?.let {
                    isCurrentFeedReporting =
                        homeViewModel.isFeedIncludedReportingList(data.feedsno)

                    var isShowingDropDownMenu by remember {
                        mutableStateOf(false)
                    }

                    var isOpenBigImage by remember {
                        mutableStateOf(false)
                    }
                    var currentImageIndex by remember {
                        mutableStateOf(0)
                    }


                    Box {
                        MainFeedCardStructure(
                            contentImageList = data.imageList,
                            contentText = data.feedcontent,
                            profileImage = data.profileimg,
                            profileId = data.userid,
                            profileName = data.userName,
                            reactionIcon = listOf(
                                R.drawable.ic_like_off,
                                R.drawable.ic_like_on
                            ),
                            reactionData = data.likeCount,
                            reactionTint = LikeColor,
                            followYN = data.followyn,
                            likeYN = data.likeyn,
                            onReactionClick = {
                                scope.launch {
                                    homeViewModel.postFeedLike(data.feedsno, it).let {
                                        Log.d(
                                            "Home-Like",
                                            "HomeMainContent: ${it.data?.code}"
                                        )
                                        Log.d(
                                            "Home-Like",
                                            "HomeMainContent: ${it.data?.message}"
                                        )
                                    }
                                }
                            },
                            otherIcons = mapOf(
                                "comment" to R.drawable.ic_comment,
                                "more" to R.drawable.ic_more
                            ),
                            hashtagList = data.hashtags,
                            navController = navController,
                            feedNo = data.feedsno,
                            deleteFeedAction = {
                                scope.launch {
                                    homeViewModel.refresh {
                                        pagingData.refresh()
                                            .let { homeViewModel.invalidateDataSource() }
                                    }
                                }
                            },
                            reportDialogCallAction = {
                                reportDialogVisible = true
                                reportTargetFeedNo = data.feedsno
                                reportedTargetName = data.userName
                            },
                            reportingCancelAction = {
                                reportListValue =
                                    homeViewModel.getReportingFeedNoValueFromKey(data.feedsno)
                                Log.d("HomeRepo", "HomeMainContent: $reportListValue")

                                if (reportListValue != null) {
                                    scope.launch {
                                        homeViewModel.postReport(
                                            data.feedsno,
                                            reportListValue!!,
                                            reportYN = false
                                        )
                                        Log.d("HomeRepo", "HomeMainContent: 뀽")
                                    }
                                    homeViewModel.reportingFeedNoRemove(it)
                                }
                            },
                            isCurrentReportingFeedsNo = isCurrentFeedReporting,
                            reportYN = data.reportyn,
                            currentScreen = SecomiScreens.HomeDetailScreen.name,
                            destinationScreen = null,
                            openBigImage = {
                                currentImageIndex = it
                                isOpenBigImage = true
                            }
                        )
                    }

                    if (isOpenBigImage) {
                        customBigImageDialog(
                            imageList = data.imageList,
                            configuration = configuration,
                            currentIndex = currentImageIndex,
                            closeAction = {
                                isOpenBigImage = it
                            }
                        )
                    }
                }
            }


        }
    }



    if (reportDialogVisible && reportTargetFeedNo != null) {
        CustomReportDialog(
            title = "${reportedTargetName}님의 게시글을 신고하기",
            reportOptions = MainFeedReportOptions,
            reportAction = { selectedOptionCode, reportDescription ->
                if (selectedOptionCode == "00") showShortToastMessage(context, "신고 사유를 선택해주세요.")
                else {
                    homeViewModel.reportingFeedAdd(reportTargetFeedNo!!, selectedOptionCode)
                    scope.launch {
                        homeViewModel.postReport(
                            feedsNo = reportTargetFeedNo!!,
                            reportType = selectedOptionCode,
                            reportContent = reportDescription,
                            reportYN = true
                        )
                        reportTargetFeedNo = null
                    }
                    reportDialogVisible = false
                }
            },
            dismissAction = {
                reportDialogVisible = false
                reportTargetFeedNo = null
            }
        )
    }




    Spacer(modifier = Modifier.height(56.dp))
}

@Composable
private fun HomeUserFeedRow(
    navController: NavController,
    friendListData: List<Friend>
) {
    val borderStroke = BorderStroke(width = 2.dp, color = Color.LightGray)

    Surface(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(start = 5.dp, top = 2.dp)
            ) {
                items(friendListData) { data ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(
                            data.userid,
                            data.profileimg,
                            Modifier,
                            borderStroke = borderStroke,
                            navController
                        )
                        ProfileName(
                            name = data.nickname,
                            fontStyle = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}
