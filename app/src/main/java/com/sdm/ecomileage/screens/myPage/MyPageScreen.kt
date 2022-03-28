package com.sdm.ecomileage.screens.myPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onClick("내 게시물")
            },
            modifier = Modifier
                .width(170.dp)
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