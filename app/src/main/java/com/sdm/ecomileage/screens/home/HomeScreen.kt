package com.sdm.ecomileage.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.sdm.ecomileage.model.homeInfo.response.Post
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.LikeColor
import com.sdm.ecomileage.ui.theme.StatusBarGreenColor
import com.sdm.ecomileage.ui.theme.TopBarColor
import com.sdm.ecomileage.utils.MainFeedReportOptions
import kotlinx.coroutines.Dispatchers
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

    val homeInfo = produceState<DataOrException<HomeInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getHomeInfo()
    }.value

    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    if (homeInfo.loading == true)
        CircularProgressIndicator()
    else if (homeInfo.data?.result != null) {
        homeInfo.data?.code?.let {
            if (it >= 200) return@let
            else {
                AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context
                )
            }
        }

        HomeScaffold(navController, homeInfo)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun HomeScaffold(
    navController: NavController,
    homeInfoResponse: DataOrException<HomeInfoResponse, Boolean, Exception>,
) {
    var pushIcon by remember {
        mutableStateOf(R.drawable.ic_push_off)
    }

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
                currentScreen = SecomiScreens.HomeScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column {
            HomeUserFeedRow(navController, homeInfoResponse.data!!.result.friendList)
            HomeMainContent(
                navController,
                homeInfoResponse.data!!.result.postList
            )
        }
    }
}

@Composable
private fun HomeMainContent(
    navController: NavController,
    postListData: List<Post>,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var scope = rememberCoroutineScope()
    val context = LocalContext.current

    var postList = SnapshotStateList<Post>()
    postList.addAll(postListData)

    var reportDialogVisible by remember {
        mutableStateOf(false)
    }

    var reportTargetFeedNo by remember {
        mutableStateOf<Int?>(null)
    }

    var isCurrentFeedReporting by remember {
        mutableStateOf(false)
    }

    var reportListValue: String?
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch(Dispatchers.IO) {
                homeViewModel.refresh(postList).let {
                    postList = it as SnapshotStateList<Post>
                    Log.d("Home", "HomeMainContent: ${postList[0].userName}")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
        ) {
            itemsIndexed(postList) { index, data ->
                //Todo : HomeInfo CardContent
                isCurrentFeedReporting = homeViewModel.isFeedIncludedReportingList(data.feedsno)

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
                    likeYN = data.likeyn,
                    onReactionClick = {
                        scope.launch {
                            homeViewModel.postFeedLike(data.feedsno, it).let {
                                Log.d("Home-Like", "HomeMainContent: ${it.data?.code}")
                                Log.d("Home-Like", "HomeMainContent: ${it.data?.message}")
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
                    reportDialogCallAction = {
                        reportDialogVisible = it
                        reportTargetFeedNo = data.feedsno
                    },
                    reportingCancelAction = {
                        reportListValue = homeViewModel.getReportingFeedNoValueFromKey(data.feedsno)
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
                    destinationScreen = null
                )
                if (index == postListData.lastIndex)
                    Spacer(modifier = Modifier.height(70.dp))
            }
        }

    }

    if (reportDialogVisible && reportTargetFeedNo != null) {
        CustomReportDialog(
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
