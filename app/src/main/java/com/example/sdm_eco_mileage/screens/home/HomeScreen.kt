package com.example.sdm_eco_mileage.screens.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.*
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.data.HomeScrollColumnViewData
import com.example.sdm_eco_mileage.data.HomeTopScrollRowViewData
import com.example.sdm_eco_mileage.model.homeInfo.response.Friend
import com.example.sdm_eco_mileage.model.homeInfo.response.HomeInfoResponse
import com.example.sdm_eco_mileage.model.homeInfo.response.Post
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.LikeColor
import com.example.sdm_eco_mileage.ui.theme.StatusBarGreenColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor
import com.example.sdm_eco_mileage.utils.Constants
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun HomeScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val homeInfo = produceState<DataOrException<HomeInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.postHomeInfo()
    }.value

    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    if (homeInfo.loading == true)
        CircularProgressIndicator()
    else if (homeInfo.data?.result != null)
        HomeScaffold(navController, homeInfo)
}

@Composable
private fun HomeScaffold(
    navController: NavController,
    homeInfoResponse: DataOrException<HomeInfoResponse, Boolean, Exception>
) {
    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = homeInfoResponse.data!!.result.userDept,
                navController = navController,
                currentScreen = SecomiScreens.HomeScreen.name,
                backgroundColor = TopBarColor,
                actionIconsList = listOf(
                    painterResource(id = R.drawable.ic_search),
                    // Todo: Ranking 왼쪽 두꺼운거 이미지 처리
                    painterResource(id = R.drawable.ic_ranking),
                    painterResource(id = R.drawable.ic_push_on),
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
            HomeMainContent(navController, homeInfoResponse.data!!.result.postList)
        }
    }
}

@Composable
private fun HomeMainContent(
    navController: NavController,
    postListData: List<Post>
) {
    var sample = remember {
        mutableStateOf(23)
    }

    LazyColumn(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        itemsIndexed(postListData) { index, data ->
            CardContent(
                currentScreen = SecomiScreens.HomeScreen.name,
                contentImage = "${Constants.BASE_IMAGE_URL}${data.photo}",
                profileImage = "${Constants.BASE_IMAGE_URL}${data.profileimg}",
                profileName = data.userName,
                reactionIcon = listOf(
                    R.drawable.ic_like_off,
                    R.drawable.ic_like_on
                ),
                reactionData = data.likeCount,
                onClickReaction = { sample.value = it },
                reactionTint = LikeColor,
                commentIcon = painterResource(id = R.drawable.ic_comment),
                needMoreIcon = true,
                moreIcon = painterResource(id = R.drawable.ic_more),
                contentText = data.feedcontent,
                hashtagList = data.hashtags,
                navController = navController,
                destinationScreen = SecomiScreens.HomeDetailScreen.name,
                feedNo = data.id
            )
            if (index == postListData.lastIndex)
                Spacer(modifier = Modifier.height(40.dp))
        }
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
                            "${Constants.BASE_IMAGE_URL}${data.photo}",
                            Modifier,
                            borderStroke = borderStroke
                        )
                        ProfileName(
                            name = data.name,
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
