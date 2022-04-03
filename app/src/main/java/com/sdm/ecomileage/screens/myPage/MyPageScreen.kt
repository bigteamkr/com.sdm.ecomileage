package com.sdm.ecomileage.screens.myPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.ProfileImage
import com.sdm.ecomileage.components.SecomiBottomBar
import com.sdm.ecomileage.components.SecomiMainFloatingActionButton
import com.sdm.ecomileage.data.ChallengeList
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*

@Composable
fun MyPageScreen(navController: NavController, systemUiController: SystemUiController) {
    SideEffect {
        systemUiController.setStatusBarColor(StatusBarGreenColor)
    }
    var selectedButton by remember {
        mutableStateOf("내 게시물")
    }

    Scaffold(
        topBar = {
            MyPageTopBar(navController)
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
        Column(
            modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
        ) {

            MyPageFilterButton(selectedButton) {
                selectedButton = it
            }

            if (selectedButton == "내 게시물") {
                Column() {
                    Text(
                        text = "오늘",
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
                        color = IndicationColor
                    )
                    Divider()
                }
            }
            if (selectedButton == "내 챌린지") {
                Column() {
                    Text(
                        text = "항목 당 일일 1회씩만 도전 가능합니다.",
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
                        color = IndicationColor
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 180.dp),
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
                                    .clickable {  },
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
                .width(170.dp)
                .height(32.dp)
                .padding(start = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == "내 게시물") PointColor else UnselectedButtonColor)
        ) {
            Text(
                text = "내 게시물",
                color = if (selectedButton == "내 게시물") Color.White else Color.Black
            )
        }
        Button(
            onClick = {
                onClick("내 챌린지")
            },
            modifier = Modifier
                .width(170.dp)
                .height(32.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButton == "내 챌린지") PointColor else UnselectedButtonColor)
        ) {
            Text(
                text = "내 챌린지",
                color = if (selectedButton == "내 챌린지") Color.White else Color.Black
            )
        }
    }
}

@Composable
private fun MyPageTopBar(navController: NavController) {
    val pointString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 21.sp
            )
        ) {
            append("32,000 ")
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
                        image = "https://blog.yena.io/assets/post-img/171123-nachoi-300.jpg",
                        modifier = Modifier
                            .padding(start = 10.dp, top = 2.dp)
                            .size(25.dp)
                    )
                    Text(
                        text = "김채영",
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