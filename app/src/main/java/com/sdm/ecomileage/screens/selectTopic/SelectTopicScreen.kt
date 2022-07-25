package com.sdm.ecomileage.screens.selectTopic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.data.ChallengeList
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.CardContentColor
import com.sdm.ecomileage.ui.theme.PointColor
import com.sdm.ecomileage.ui.theme.TopBarColorOrigin

@Composable
fun SelectTopicScreen(
    navController: NavController,
    systemUiController: SystemUiController
) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    var selectedCategory = ""

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "주제를 선택해주세요.",
                currentScreen = SecomiScreens.SelectTopicScreen.name,
                contentColor = Color.White,
                navController = navController,
                backgroundColor = TopBarColorOrigin
            )
        },
        floatingActionButton = {
            SelectTopicFloatingActionButton(
                navController = navController,
                isClicked = isClicked,
                selectedCategory = selectedCategory
            )
        }
    ) {
        ChallengeLayout(
            navController = navController,
            click = { isClicked = it },
            selectCategory = { selectedCategory = it }
        )
    }
}

@Composable
fun ChallengeLayout(
    navController: NavController,
    click: (Boolean) -> Unit,
    selectCategory: (String) -> Unit
) {
    val configuration = LocalConfiguration.current

    val pointString = AnnotatedString(
        "5 ",
        spanStyle = SpanStyle(
            color = CardContentColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    )
    val epString = AnnotatedString(
        "EP",
        spanStyle = SpanStyle(
            color = CardContentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal
        )
    )

    var isClick by remember {
        mutableStateOf(-1)
    }
    var isBackOnceClick by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember { MutableInteractionSource() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        userScrollEnabled = false
    ) {
        itemsIndexed(ChallengeList) { index, photo ->

            val text = when (photo) {
                R.drawable.image_daily -> "일상생활"
                R.drawable.image_empty_dish -> "빈그릇 챌린지"
                R.drawable.image_public_transport -> "대중교통 챌린지"
                R.drawable.image_thermos -> "개인 텀블러 챌린지"
                R.drawable.image_label_detach -> "라벨지 떼기 챌린지"
                R.drawable.image_basket -> "장바구니 챌린지"
                R.drawable.image_pull_a_plug -> "코드뽑기 챌린지"
                R.drawable.image_empty_bottle -> "용기 재활용 챌린지"
                R.drawable.image_upcycling -> "업사이클링 챌린지"
                R.drawable.image_back -> "돌아가기"
                else -> "챌린지"
            }
            val paddingModifier = if (index % 2 == 0) Modifier.padding(
                end = 10.dp,
                bottom = 10.dp
            ) else Modifier.padding(start = 10.dp, bottom = 10.dp)

            val clickPaddingModifier = if (index % 2 == 0) Modifier.padding(
                top = 15.dp,
                end = 20.dp
            ) else Modifier.padding(top = 15.dp, end = 10.dp)

            Box(contentAlignment = Alignment.TopEnd) {
                Card(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = if (text == "돌아가기") !isBackOnceClick else true
                        ) {
                            if (text != "돌아가기" && isClick != index) {
                                isClick = index
                                selectCategory(text)
                                click(true)
                            } else if (text != "돌아가기") {
                                isClick = -1
                                selectCategory("")
                                click(false)
                            } else {
                                isBackOnceClick = true
                                navController.popBackStack()
                            }
                        }
                        .fillMaxSize()
                        .then(paddingModifier),
                    shape = RoundedCornerShape(11.dp),
                    elevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Image(
                            painter = painterResource(id = photo), contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = text, letterSpacing = 1.4.sp)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }

                AnimatedVisibility(
                    visible = isClick == index,
                    enter = fadeIn(tween(100))
                    ) {
                    Surface(modifier = clickPaddingModifier) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "$photo 관련 글 작성하기",
                            modifier = Modifier.size(30.dp),
                            tint = PointColor
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun SelectTopicFloatingActionButton(
    navController: NavController,
    isClicked: Boolean,
    selectedCategory: String
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = isClicked,
        enter = slideInVertically { with(density) { -10.dp.roundToPx() } },
        exit = ExitTransition.None
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(SecomiScreens.HomeAddScreen.name + "/$selectedCategory") },
            shape = RoundedCornerShape(11.dp),
            backgroundColor = PointColor,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text("글 작성하기")
            }
        }
    }
}

// :- item 1개만 가로로 크게 넣어야할 때 코드
//        item(
//            span = { GridItemSpan(maxLineSpan) }
//        ) {
//            Row(modifier = Modifier
//                .padding(8.dp)
//                .fillMaxWidth()
//            ) {
//                Box(
//                    contentAlignment = Alignment.BottomStart
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_daily),
//                        contentDescription = ""
//                    )
//
//                    Column(
//                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
//                    ) {
//                        Text(
//                            text = "일상생활",
//                            color = Color.White,
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Text(
//                            text = pointString + epString,
//                            modifier = Modifier.padding(top = 1.dp)
//                        )
//                    }
//                }
//            }
//        }


//Surface(shape = RoundedCornerShape(11.dp)) {
//    Image(
//        painter = painterResource(id = photo),
//        contentDescription = "",
//        modifier = Modifier.fillMaxSize(),
//    )
//}
//Column {
//    Text(
//        text = text,
//        color = Color.White,
//        fontSize = 15.sp,
//        fontWeight = FontWeight.Bold
//    )
//    if (text != "돌아가기")
//        Text(
//            text = pointString + epString,
//            modifier = Modifier.padding(top = 1.dp)
//        )
//    else
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = "",
//            modifier = Modifier.padding(end = 5.dp),
//            tint = Color.White
//        )
//}