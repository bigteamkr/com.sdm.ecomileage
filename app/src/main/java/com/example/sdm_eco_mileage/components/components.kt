package com.example.sdm_eco_mileage.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.BottomSelectedColor
import com.example.sdm_eco_mileage.ui.theme.BottomUnSelectedColor
import com.example.sdm_eco_mileage.ui.theme.PointColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor

//Todo : TopAppBar 다시 정리하기 -> Left / Center / Right 로 Composable 함수 받기
@Preview
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left - Title or Icons

            Row(modifier = Modifier.padding(start = 5.dp)) {
                when (navigationIcon) {
                    null -> {
                        if (currentScreen == SecomiScreens.HomeScreen.name)
                            Text(
                                text = title,
                                color = contentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        else
                            Box {}
                    }

                    else -> {
                        Icon(
                            painter = navigationIcon,
                            contentDescription = "navigation Icon (back or cancel)",
                            tint = contentColor
                        )
                    }
                }
            }

            //Center title or empty

            Row {
                when (navigationIcon) {
                    null -> {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_topbar_leaves_2),
                                contentDescription = "top bar decoration"
                            )
                        }
                    }
                    else -> {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_topbar_leaves_2),
                                contentDescription = "top bar decoration"
                            )
                            Text(
                                text = title,
                                color = contentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // Right - Icons or empty
            Row(modifier = Modifier.padding(end = 5.dp)) {
                when (actionIconsList) {
                    null -> {
                        Box {}
                    }

                    else -> {
                        actionIconsList.forEach { icons ->
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
}

@Composable
fun SecomiMainFloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(SecomiScreens.HomeDetailScreen.name)
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
    tintColor: Color
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
                    reactionData
                } else {
                    iconResource.value = iconResourceList[0]
                    reactionData
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
    fontWeight: FontWeight = FontWeight.Normal
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
fun SecomiBottomBar(navController: NavController, currentScreen: String) {
    BottomAppBar(
        backgroundColor = Color.White,
        cutoutShape = CircleShape
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


        val iconModifier = Modifier.size(22.dp)
        val labelModifier = Modifier.padding(start = 1.dp, top = 5.dp)
        val labelStyle = MaterialTheme.typography.overline
        val letterSpacing = 0.7.sp

        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            BottomNavigationItem(
                selected = selectedIconName.value == SecomiScreens.HomeScreen.name,
                onClick = {
                    selectedIconName.value = SecomiScreens.HomeScreen.name
                    navController.navigate(SecomiScreens.HomeScreen.name) {
                        launchSingleTop
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = homeIcon.value),
                        contentDescription = "Home Navigate Button",
                        modifier = iconModifier
                    )
                },
                modifier = Modifier,
                label = {
                    Text(
                        text = "HOME",
                        modifier = labelModifier,
                        fontSize = 11.sp,
                        letterSpacing = letterSpacing,
                        style = labelStyle
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = BottomSelectedColor,
                unselectedContentColor = BottomUnSelectedColor
            )

            BottomNavigationItem(
                selected = selectedIconName.value == SecomiScreens.EducationScreen.name,
                onClick = {
                    selectedIconName.value = SecomiScreens.EducationScreen.name
                    navController.navigate(SecomiScreens.EducationScreen.name) {
                        launchSingleTop
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = educationIcon.value),
                        contentDescription = "Education Navigate Button",
                        modifier = iconModifier
                    )
                },
                modifier = Modifier,
                label = {
                    Text(
                        text = "EDUCATION",
                        modifier = labelModifier,
                        fontSize = 11.sp,
                        letterSpacing = 0.2.sp,
                        style = labelStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Visible
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = BottomSelectedColor,
                unselectedContentColor = BottomUnSelectedColor
            )

            Spacer(modifier = Modifier.width(35.dp))

            BottomNavigationItem(
                selected = selectedIconName.value == SecomiScreens.EventScreen.name,
                onClick = {
                    selectedIconName.value = SecomiScreens.EventScreen.name
                    navController.navigate(SecomiScreens.EventScreen.name) {
                        launchSingleTop
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = eventIcon.value),
                        contentDescription = "Event Navigate Button",
                        modifier = iconModifier
                    )
                },
                modifier = Modifier,
                label = {
                    Text(
                        text = "EVENT",
                        modifier = labelModifier,
                        fontSize = 11.sp,
                        letterSpacing = letterSpacing,
                        style = labelStyle
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = BottomSelectedColor,
                unselectedContentColor = BottomUnSelectedColor
            )

            BottomNavigationItem(
                selected = selectedIconName.value == SecomiScreens.MyPageScreen.name,
                onClick = {
                    selectedIconName.value = SecomiScreens.MyPageScreen.name
                    navController.navigate(SecomiScreens.MyPageScreen.name) {
                        launchSingleTop
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = myPageIcon.value),
                        contentDescription = "My Page Navigate Button",
                        modifier = iconModifier
                    )
                },
                modifier = Modifier,
                label = {
                    Text(
                        text = "MY PAGE",
                        modifier = labelModifier,
                        fontSize = 11.sp,
                        letterSpacing = letterSpacing,
                        style = labelStyle
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = BottomSelectedColor,
                unselectedContentColor = BottomUnSelectedColor
            )
        }
    }
}