package com.example.sdm_eco_mileage.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun HomeScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeInfo = produceState<DataOrException<HomeInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = viewModel.postHomeInfo()
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
        items(postListData) { data ->
            CardContent(
                currentScreen = SecomiScreens.HomeScreen.name,
                contentImage = HomeScrollColumnViewData[0].image,
                profileImage = HomeScrollColumnViewData[1].image,
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
                contentText = "임시라네! 햄이네라네 ! 재밌다네 ! 아이네 방송 꼭 보라네 !",
                navController = navController,
                navigateScreen = SecomiScreens.HomeDetailScreen.name
            )
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
                        ProfileImage(data.photo, Modifier, borderStroke = borderStroke)
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


