package com.example.sdm_eco_mileage.screens.myPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.ProfileImage
import com.example.sdm_eco_mileage.components.SecomiBottomBar
import com.example.sdm_eco_mileage.components.SecomiMainFloatingActionButton
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.PointColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun MyPageScreen(navController: NavController, systemUiController: SystemUiController) {
    Scaffold(
        topBar = {
            MyPageTopBar()
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
            modifier = Modifier.padding(top = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(180.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PointColor)
                ) {
                    Text(text = "내 게시물", color = Color.White)
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(180.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PointColor)
                ) {
                    Text(text = "내 챌린지", color = Color.White)
                }
            }
            Text(text = "오늘", modifier = Modifier.padding(15.dp), color = Color.LightGray)
            Divider()

        }
    }
}

@Preview
@Composable
private fun MyPageTopBar() {
    val pointString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
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
            .height(90.dp)
            .background(
                Brush.verticalGradient(TopBarColor)
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.padding(end = 10.dp, bottom = 5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_setting),
                    contentDescription = "setting",
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_push_on),
                    contentDescription = "push on",
                    modifier = Modifier.size(25.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
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