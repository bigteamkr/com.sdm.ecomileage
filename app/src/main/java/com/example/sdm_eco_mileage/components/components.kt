package com.example.sdm_eco_mileage.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.*

@Composable
fun SecomiTopAppBar(
    title: String = "연복중중학교",
    navigationIcon: Painter? = null,
    currentScreen: String = SecomiScreens.HomeScreen.name,
    backgroundColor: List<Color> = TopBarColor,
    actionIconsList: List<Painter>? = null,
    navController: NavController = NavController(LocalContext.current),
    contentColor: Color = Color.White
) {
    // Todo : 다시 수정 .. Icon 을 key-value 로 받아서 key 에 따른 행동 추가하기
    // navigation Icon = start(left) icon
    // actionIconsList = end(right) icons

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                Brush.verticalGradient(backgroundColor)
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (backgroundColor == TopBarColor)
                Icon(
                    painter = painterResource(id = R.drawable.ic_topbar_leaves_2),
                    contentDescription = "top bar decoration",
                    modifier = Modifier.size(80.dp),
                    tint = LeavesColor
                )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Left - Title or Icons
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (navigationIcon != null) {
                        Icon(
                            painter = navigationIcon,
                            contentDescription = "navigation Icon (back or cancel)",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                                .clickable {
                                    navController.popBackStack()
                                },
                            tint = contentColor
                        )
                    }

                    when (currentScreen) {
                        SecomiScreens.HomeScreen.name -> {
                            AppBarTitleText(title, Modifier, contentColor, 15.sp)
                        }
                        SecomiScreens.EducationScreen.name -> {
                            AppBarTitleText(title, Modifier, contentColor, 15.sp)
                        }
                    }
                }

                //Center title or empty
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    when (currentScreen) {
                        SecomiScreens.HomeAddScreen.name -> {
                            AppBarTitleText(
                                title,
                                Modifier.padding(end = 25.dp),
                                contentColor,
                                18.sp
                            )
                        }

                        SecomiScreens.HomeDetailScreen.name -> {
                            AppBarTitleText(
                                title,
                                Modifier.padding(end = 25.dp),
                                contentColor,
                                18.sp
                            )
                        }

                        SecomiScreens.RankingScreen.name -> {
                            AppBarTitleText(
                                title,
                                Modifier.padding(end = 25.dp),
                                contentColor,
                                18.sp
                            )
                        }
                    }
                }

                // Right - Icons or empty
                Row(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    actionIconsList?.forEachIndexed { index, icons ->
                        if (index == actionIconsList.lastIndex)
                            Image(painter = icons, contentDescription = "action icons")
                        else
                            Icon(
                                painter = icons,
                                contentDescription = "action icons",
                                tint = contentColor,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp)
                            )
                    }
                }
            }
        }
    }
}


@Composable
private fun AppBarTitleText(
    title: String,
    modifier: Modifier = Modifier,
    contentColor: Color,
    fontSize: TextUnit
) {
    Text(
        text = title,
        modifier = modifier,
        color = contentColor,
        fontSize = fontSize,
        style = MaterialTheme.typography.h4,
        fontWeight = FontWeight.SemiBold,
        maxLines = 1
    )
}

@Composable
fun SecomiMainFloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(SecomiScreens.HomeAddScreen.name)
        },
        shape = CircleShape,
        backgroundColor = PointColor,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Home Content Add button"
        )
    }
}

@Composable
fun ReactionIconText(
    iconResourceList: List<Int>,
    reactionData: Int,
    onClickReaction: (Int) -> Unit,
    tintColor: Color,
) {
    val isChecked = remember {
        mutableStateOf(false)
    }

    val iconResource = remember {
        mutableStateOf(iconResourceList[0])
    }

    val isReactionEnable = iconResourceList.size > 1

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        IconToggleButton(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                if (isChecked.value && iconResourceList.size > 1) {
                    iconResource.value = iconResourceList[1]
                    //Todo : 좋아요 좋아요취소 API
                } else {
                    iconResource.value = iconResourceList[0]
                }
            },
            modifier = Modifier.size(22.dp),
            enabled = isReactionEnable
        ) {
            Icon(
                painter = painterResource(id = iconResource.value),
                contentDescription = "null",
                tint = tintColor
            )
        }

        Text(
            text = "${reactionData}",
            modifier = Modifier
                .padding(start = 45.dp),
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Normal,
            color = tintColor
        )
    }
}

@Composable
fun ProfileName(
    name: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    fontStyle: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = name,
        modifier = modifier
            .width(55.dp)
            .padding(top = 2.dp),
        style = fontStyle,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImage(
    image: String,
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(
        width = 0.dp,
        color = Color.LightGray
    )
) {
    Surface(
        onClick = { },
        modifier = modifier.size(55.dp),
        shape = CircleShape,
        border = borderStroke
    ) {
        Image(
            painter = rememberImagePainter(image),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CardContent(
    currentScreen: String,
    contentImage: String = "",
    profileImage: String = "",
    profileName: String = "세코미",
    reactionIcon: List<Int>,
    reactionData: Int,
    onClickReaction: (Int) -> Unit,
    reactionTint: Color,
    commentIcon: Painter,
    needMoreIcon: Boolean,
    moreIcon: Painter?,
    contentText: String = " ",
    hashtagList: List<String>?,
    navController: NavController,
    destinationScreen: String,
    feedNo: Int
) {
    // Todo : Card 에 이미지도 LazyRow
    // Todo : CardContent fix 방향 : 1. 각 아이콘의 Reaction 을 람다식으로 받는다. 2. ReactionIcon 받는 방법을 수정한다.
    Card(
        modifier = Modifier
            .padding(5.dp)
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .height(300.dp)
            .clickable {
                if (currentScreen == SecomiScreens.EducationScreen.name) {
                    run { onClickReaction }
                }
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = contentImage),
                contentDescription = "Card Content Main Image",
                modifier = Modifier
                    .fillMaxHeight(0.65f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Inside
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
                        fontWeight = FontWeight.Normal,
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

                    Surface(
                        modifier = Modifier
                            .clickable {
                                if (currentScreen == SecomiScreens.HomeScreen.name)
                                    navController.navigate("$destinationScreen/$feedNo") {
                                        launchSingleTop
                                    }
                            },
                        shape = RoundedCornerShape(percent = 30)
                    ) {
                        Icon(
                            painter = commentIcon,
                            contentDescription = "Comment button",
                            modifier = Modifier
                                .padding(start = 2.dp, top = 1.dp)
                                .size(24.dp),
                            tint = Color.LightGray
                        )
                    }

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
            if (!hashtagList.isNullOrEmpty())
                Row(Modifier.padding(start = 12.dp, end = 10.dp, top = 7.dp)) {
                    hashtagList.forEachIndexed { index, tag ->
                        Text(
                            text = tag,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = TagColor,
                                textAlign = TextAlign.Justify
                            )
                        )
                        if (index != hashtagList.lastIndex)
                            Spacer(modifier = Modifier.width(5.dp))
                    }
                }
        }
    }
}

@Composable
fun SecomiBottomBar(navController: NavController, currentScreen: String) {
    BottomAppBar(
        backgroundColor = Color.White,
        cutoutShape = CircleShape,
        elevation = 12.dp
    ) {
        val selectedIconName = remember {
            mutableStateOf(currentScreen)
        }

        val homeIcon = remember {
            mutableStateOf(R.drawable.ic_home_off)
        }
        val educationIcon = remember {
            mutableStateOf(R.drawable.ic_edu_off)
        }

        val eventIcon = remember {
            mutableStateOf(R.drawable.ic_event_off)
        }
        val myPageIcon = remember {
            mutableStateOf(R.drawable.ic_mypage_off)
        }


        val iconModifier = Modifier.size(25.dp)
        val labelModifier = Modifier.padding(start = 1.dp, top = 10.dp)
        val labelStyle = MaterialTheme.typography.overline
        val letterSpacing = 0.7.sp

        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                letterSpacing,
                labelStyle,
                currentScreenButton = SecomiScreens.HomeScreen.name,
                iconResource = homeIcon,
                label = "HOME",
                description = "Home Navigate Button"
            )

            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                letterSpacing,
                labelStyle,
                currentScreenButton = SecomiScreens.EducationScreen.name,
                iconResource = educationIcon,
                label = "EDUCATION",
                description = "Education Navigate Button"
            )
            Spacer(modifier = Modifier.width(35.dp))

            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                letterSpacing,
                labelStyle,
                currentScreenButton = SecomiScreens.EventScreen.name,
                iconResource = eventIcon,
                label = "EVENT",
                description = "Event Navigate Button"
            )

            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                letterSpacing,
                labelStyle,
                currentScreenButton = SecomiScreens.MyPageScreen.name,
                iconResource = myPageIcon,
                label = "MY PAGE",
                description = "My Page Navigate Button"
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    selectedIconName: MutableState<String>,
    navController: NavController,
    iconModifier: Modifier,
    labelModifier: Modifier,
    letterSpacing: TextUnit,
    labelStyle: TextStyle,
    currentScreenButton: String,
    iconResource: MutableState<Int>,
    label: String,
    description: String
) {
    BottomNavigationItem(
        selected = selectedIconName.value == currentScreenButton,
        onClick = {
            selectedIconName.value = currentScreenButton
            navController.navigate(currentScreenButton) {
                launchSingleTop
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = iconResource.value),
                contentDescription = description,
                modifier = iconModifier
            )
        },
        modifier = Modifier,
        label = {
            Text(
                text = label,
                modifier = labelModifier,
                fontSize = 9.5.sp,
                letterSpacing = letterSpacing,
                style = labelStyle
            )
        },
        alwaysShowLabel = true,
        selectedContentColor = BottomSelectedColor,
        unselectedContentColor = BottomUnSelectedColor
    )
}