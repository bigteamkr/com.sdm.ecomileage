package com.sdm.ecomileage.screens.mileage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.MileageSwipeButton
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.LoginGreyTextColor
import com.sdm.ecomileage.ui.theme.NavGreyColor

@Composable
fun MileageScreen(navController: NavController, systemUiController: SystemUiController) {
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "마일리지 랭킹",
                backgroundColor = listOf(Color.White, Color.White),
                navigationIcon = rememberImagePainter(R.drawable.ic_back_arrow),
                currentScreen = SecomiScreens.MileageRanking.name,
                navController = navController,
                contentColor = NavGreyColor,
                actionIconsList = listOf { MileageSwipeButton() }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "2022.05.01 ~ 2022.05.31", color = LoginGreyTextColor)

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Column() {
                    Spacer(modifier = Modifier.fillMaxHeight(0.17f))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(5),
                        elevation = 5.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "연북중학교",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "3,456,789 EP",
                                modifier = Modifier.padding(top = 10.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Image(
                    painter = rememberImagePainter(R.drawable.ic_mileage_crown),
                    contentDescription = "1등",
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.19f)
                )
            }

            data class Sample(val logo: Painter, val school: String, val point: String)

            var sampleList = listOf(
                Sample(
                    rememberImagePainter(R.drawable.ic_silver_medal),
                    "응암초등학교",
                    "2,345,678 EP"
                ),
                Sample(rememberImagePainter(R.drawable.ic_bronze_medal), "연가중학교", "1,234,567 EP"),
                Sample(rememberImagePainter(R.drawable.ic_highschool), "충암고등학교", "1,223,344 EP"),
                Sample(rememberImagePainter(R.drawable.ic_highschool), "가재울고등학교", "1,000,078 EP"),
                Sample(rememberImagePainter(R.drawable.ic_middleschool), "신연중학교", "987,654 EP"),
                Sample(rememberImagePainter(R.drawable.ic_middleschool), "홍은중학교", "945,678 EP"),
                Sample(rememberImagePainter(R.drawable.ic_middleschool), "가재고등학교", "901,234 EP"),
                Sample(rememberImagePainter(R.drawable.ic_highschool), "한국한성화교중고등학교", "845,678 EP"),
                Sample(rememberImagePainter(R.drawable.ic_highschool), "한성고등학교", "843,234 EP"),
                Sample(rememberImagePainter(R.drawable.ic_middleschool), "인창중학교", "754,678 EP"),
                Sample(rememberImagePainter(R.drawable.ic_middleschool), "서연중학교", "701,244 EP"),
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 20.dp, top = 35.dp),
                shape = RoundedCornerShape(2),
                elevation = 5.dp
            ) {
                LazyColumn(
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(sampleList) { index, data ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                if (index >= 3) {
                                    Text(text = "${index + 1}", fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                if (index == 3)
                                    Spacer(modifier = Modifier.height(25.dp))

                                Image(
                                    painter = data.logo,
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "logo"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = data.school,
                                    fontWeight = if (index < 3) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                            Text(
                                text = data.point,
                                fontWeight = if (index < 3) FontWeight.Bold else FontWeight.SemiBold
                            )
                        }

                    }
                }
            }

        }
    }
}