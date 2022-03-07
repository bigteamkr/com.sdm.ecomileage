package com.example.sdm_eco_mileage.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.*
import com.example.sdm_eco_mileage.data.SampleHomeColumn
import com.example.sdm_eco_mileage.data.SampleHomeRow
import com.example.sdm_eco_mileage.data.HomeScrollColumnViewData
import com.example.sdm_eco_mileage.data.HomeTopScrollRowViewData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.LikeColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "어쨋든 엄청 긴 이름 학교",
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
            HomeUserFeedRow(navController, sampleDataForLazyRow)
            HomeMainContent(navController, sampleDataForLazyColumn)
        }
    }
}


@Composable
private fun HomeMainContent(
    navController: NavController,
    sampleDataForLazyHomeColumn: List<SampleHomeColumn>
) {
    var sample = remember {
        mutableStateOf(23)
    }

    LazyColumn(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        items(sampleDataForLazyHomeColumn) { data ->
            CardContent(
                data,
                contentImage = data.image,
                profileImage = data.image,
                profileName = data.name,
                reactionIcon = listOf(
                    R.drawable.ic_like_off,
                    R.drawable.ic_like_on
                ),
                reactionData = sample.value,
                onClickReaction = { sample.value = it },
                reactionTint = LikeColor,
                commentIcon = painterResource(id = R.drawable.ic_comment),
                needMoreIcon = true,
                moreIcon = painterResource(id = R.drawable.ic_more),
                contentText = data.content,
                navigate = {
                    navController.navigate(it) {
                        popUpTo(SecomiScreens.HomeScreen.name) { inclusive = false }
                    }
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(56.dp))
}

//Todo : Card Content 완벽하게 컴포넌트화 하기

@Composable
private fun CardContent(
    data: SampleHomeColumn = HomeScrollColumnViewData[0],
    contentImage: String = "이거 고쳐라",
    profileImage: String = "",
    profileName: String = "",
    reactionIcon: List<Int>,
    reactionData: Int,
    onClickReaction: (Int) -> Unit,
    reactionTint: Color,
    commentIcon: Painter,
    needMoreIcon: Boolean,
    moreIcon: Painter?,
    contentText: String = "",
    navigate: (String) -> Unit
) {
    Column {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .padding(end = 2.dp)
                .fillMaxWidth()
                .height(300.dp)
                .clickable {
                    navigate(SecomiScreens.HomeDetailScreen.name)
                },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White,
            elevation = 12.dp
        ) {
            Column {
                Image(
                    painter = rememberImagePainter(data = data.image),
                    contentDescription = "Card Content Main Image",
                    modifier = Modifier
                        .fillMaxHeight(0.65f),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(5.dp))
                        ProfileImage(
                            image = profileImage,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        ProfileName(
                            name = profileName,
                            modifier = Modifier.padding(top = 2.dp, bottom = 5.dp),
                            fontStyle = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 1.dp)
                    ) {
                        ReactionIconText(
                            iconResourceList = reactionIcon,
                            reactionData = reactionData,
                            onClickReaction = { onClickReaction(reactionData + 1) },
                            tintColor = reactionTint
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = commentIcon,
                            contentDescription = "Comment button",
                            modifier = Modifier
                                .padding(start = 5.dp, top = 2.dp)
                                .size(24.dp),
                            tint = Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if (needMoreIcon)
                            Icon(
                                painter = moreIcon!!,
                                contentDescription = "More button",
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .size(22.dp),
                                tint = Color.LightGray
                            )
                    }

                }
                Text(
                    text = contentText,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 10.dp,
                        top = 7.dp
                    ),
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
private fun HomeUserFeedRow(navController: NavController, sampleDataForLazyHomeRow: List<SampleHomeRow>) {
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
                items(sampleDataForLazyHomeRow) { data ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(data.image, Modifier, borderStroke = borderStroke)
                        ProfileName(
                            name = data.name,
                            fontStyle = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}


