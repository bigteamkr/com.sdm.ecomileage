package com.sdm.ecomileage.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.data.HomeScrollColumnViewData
import com.sdm.ecomileage.data.HomeTopScrollRowViewData
import com.sdm.ecomileage.model.homeInfo.response.Friend
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.model.homeInfo.response.Post
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.LikeColor
import com.sdm.ecomileage.ui.theme.StatusBarGreenColor
import com.sdm.ecomileage.ui.theme.TopBarColor
import com.sdm.ecomileage.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
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
        HomeScaffold(navController, homeViewModel, homeInfo)
    }
}

@Composable
private fun HomeScaffold(
    navController: NavController,
    homeViewModel: HomeViewModel,
    homeInfoResponse: DataOrException<HomeInfoResponse, Boolean, Exception>
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
                actionIconsList = mapOf(
                    "search" to painterResource(id = R.drawable.ic_search),
                    "ranking" to painterResource(id = R.drawable.ic_ranking),
                    "push" to painterResource(id = pushIcon)
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
        val sampleDataForLazyRow = HomeTopScrollRowViewData
        val sampleDataForLazyColumn = HomeScrollColumnViewData

        Column {
            HomeUserFeedRow(navController, homeInfoResponse.data!!.result.friendList)
            HomeMainContent(navController, homeViewModel, homeInfoResponse.data!!.result.postList)
        }
    }
}

@Composable
private fun HomeMainContent(
    navController: NavController,
    homeViewModel: HomeViewModel,
    postListData: List<Post>
) {
    var scope = rememberCoroutineScope()
    val context = LocalContext.current

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


    LazyColumn(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        itemsIndexed(postListData) { index, data ->
            //Todo : HomeInfo CardContent
            isCurrentFeedReporting = homeViewModel.isFeedIncludedReportingList(data.feedsno)

            MainFeedCardStructure(
                contentImageList = data.imageList,
                contentText = data.feedcontent,
                profileImage = data.profileimg,
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

                    if (reportListValue != null){
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

    if (reportDialogVisible && reportTargetFeedNo != null) {
        CustomReportDialog(
            reportAction = {
                homeViewModel.reportingFeedAdd(reportTargetFeedNo!!, it)

                scope.launch {
                    homeViewModel.postReport(
                        feedsNo = reportTargetFeedNo!!,
                        reportType = it,
                        reportYN = true
                    )
                    reportTargetFeedNo = null
                }

                reportDialogVisible = false
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
                            "${Constants.BASE_IMAGE_URL}${data.profileimg}",
                            Modifier,
                            borderStroke = borderStroke
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
