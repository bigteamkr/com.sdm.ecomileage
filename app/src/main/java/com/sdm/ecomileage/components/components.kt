package com.sdm.ecomileage.components

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sdm.ecomileage.R
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*

@Composable
fun SecomiTopAppBar(
    title: String,
    navigationIcon: Painter? = null,
    currentScreen: String,
    backgroundColor: List<Color> = TopBarColor,
    actionIconsList: Map<String, Painter>? = null,
    navController: NavController,
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

                        SecomiScreens.DiaryScreen.name -> {
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        actionIconsList?.forEach { (key, painter) ->
                            when (key) {
                                "search" -> {
                                    Surface(
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(5.dp)
                                            .clickable {
                                                navController.navigate(SecomiScreens.SearchScreen.name) {
                                                    popUpTo(SecomiScreens.HomeScreen.name)
                                                }
                                            },
                                        color = Color.Transparent
                                    ) {
                                        Icon(
                                            painter = painter,
                                            contentDescription = "Search icon",
                                            tint = contentColor,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                                "ranking" -> {
                                    Surface(
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(5.dp)
                                            .clickable {
                                                navController.navigate(SecomiScreens.RankingScreen.name) {
                                                    popUpTo(SecomiScreens.HomeScreen.name)
                                                }
                                            },
                                        color = Color.Transparent
                                    ) {
                                        Icon(
                                            painter = painter,
                                            contentDescription = "Ranking icon",
                                            tint = contentColor,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        )
                                    }
                                }
                                "push" -> {
                                    Surface(
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(5.dp)
                                            .clickable {
                                                navController.navigate(SecomiScreens.NoticeScreen.name) {
                                                    popUpTo(SecomiScreens.HomeScreen.name)
                                                }
                                            },
                                        color = Color.Transparent
                                    ) {
                                        Image(
                                            painter = painter,
                                            contentDescription = "Push icon",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.FillBounds
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
fun CustomReaction(
    modifier: Modifier = Modifier,
    iconResourceList: List<Int>,
    reactionData: Int,
    onClickReaction: (Boolean) -> Unit,
    tintColor: Color,
    likeYN: Boolean,
) {
    var isChecked by remember {
        mutableStateOf(likeYN)
    }
    var _reactionData by remember {
        mutableStateOf(reactionData)
    }
    val isReactionEnable = iconResourceList.size > 1

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconToggleButton(
            checked = isChecked,
            onCheckedChange = {
                Log.d("Home-Like", "CustomIconText: likeYN was $likeYN / isChecked was $isChecked")
                isChecked = !isChecked
                Log.d("Home-Like", "CustomIconText: likeYN is $likeYN / isChecked is $isChecked")

                onClickReaction(isChecked)
                _reactionData = if (isChecked) _reactionData + 1 else _reactionData - 1
            },
            modifier = Modifier.size(24.dp),
            enabled = isReactionEnable
        ) {
            Icon(
                painter = painterResource(id = if (!isChecked) iconResourceList[0] else iconResourceList[1]),
                contentDescription = if (isChecked) "좋아요 취소" else "좋아요",
                tint = tintColor
            )
        }

        Text(
            text = "$_reactionData",
            modifier = Modifier
                .padding(start = 45.dp),
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Normal,
            color = tintColor
        )
    }
}

@Composable
fun CustomIconText(
    iconResource: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tintColor: Color,
    textData: String
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = tintColor
        )

        Text(
            text = textData,
            modifier = Modifier
                .padding(start = (45 + (textData.length * 2)).dp, bottom = 3.dp)
                .then(modifier),
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Normal,
            color = tintColor
        )
    }
}

@Composable
fun ProfileName(
    name: String?,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    fontStyle: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = name ?: "세코미",
        modifier = modifier
            .padding(top = 2.dp),
        fontSize = fontSize,
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
fun MainFeedCardStructure(
    contentImageList: List<String>,
    contentText: String,
    profileImage: String,
    profileName: String,
    reactionIcon: List<Int>,
    reactionData: Int,
    reactionTint: Color,
    likeYN: Boolean,
    reportYN: Boolean,
    onReactionClick: (Boolean) -> Unit,
    otherIcons: Map<String, Int>,
    hashtagList: List<String>?,
    navController: NavController,
    feedNo: Int,
    isCurrentReportingFeedsNo: Boolean,
    reportDialogCallAction: (Boolean) -> Unit,
    reportingCancelAction: (Int) -> Unit,
    currentScreen: String,
    destinationScreen: String?
) {
    var isReportingCard by remember { mutableStateOf(isCurrentReportingFeedsNo) }
    LaunchedEffect(key1 = isCurrentReportingFeedsNo) {
        isReportingCard = isCurrentReportingFeedsNo
    }

    when {
        reportYN -> Box { }
        isReportingCard -> {
            ReportedFeed(feedNo) {
                reportingCancelAction(it)
            }
        }
        else -> {
            MainCardFeed(
                contentImageList,
                profileImage,
                profileName,
                null,
                null,
                reactionIcon,
                reactionData,
                onReactionClick,
                reactionTint,
                likeYN,
                null,
                otherIcons,
                navController,
                reportDialogCallAction,
                currentScreen,
                feedNo,
                contentText,
                hashtagList,
                destinationScreen
            )

        }
    }
}

@Composable
fun MainCardFeed(
    contentImageList: List<String>,
    profileImage: String,
    profileName: String,
    educationTitle: String?,
    educationTime: String?,
    reactionIcon: List<Int>?,
    reactionData: Int?,
    onReactionClick: ((Boolean) -> Unit)?,
    reactionTint: Color?,
    likeYN: Boolean?,
    colorIcon: (@Composable () -> Unit)?,
    otherIcons: (Map<String, Int>)?,
    navController: NavController,
    reportDialogCallAction: ((Boolean) -> Unit)?,
    currentScreen: String,
    feedNo: Int,
    contentText: String,
    hashtagList: List<String>?,
    destinationScreen: String?
) {
    val heightModifier = if (currentScreen == SecomiScreens.EducationScreen.name) 230.dp else 355.dp

    val navigatingModifier = if (destinationScreen != null) Modifier.clickable {
        navController.navigate(destinationScreen) {
            launchSingleTop
            popUpTo(currentScreen)
        }
    } else Modifier

    Card(
        modifier = Modifier
            .padding(5.dp)
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .then(navigatingModifier),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightModifier)
            ) {
                CardImageRow(contentImageList, educationTitle, educationTime, currentScreen)
            }
            CardWriterInformation(
                profileImage,
                profileName,
                reactionIcon,
                reactionData,
                onReactionClick,
                reactionTint,
                likeYN,
                colorIcon,
                otherIcons,
                navController,
                reportDialogCallAction,
                currentScreen,
                feedNo
            )
            CardContent(contentText, hashtagList)
        }
    }
}

@Composable
fun CardWriterInformation(
    profileImage: String,
    profileName: String,
    reactionIcon: List<Int>?,
    reactionData: Int?,
    onReactionClick: ((Boolean) -> Unit)?,
    reactionTint: Color?,
    likeYN: Boolean?,
    colorIcon: (@Composable () -> Unit)?,
    otherIcons: (Map<String, Int>)?,
    navController: NavController?,
    reportDialogCallAction: ((Boolean) -> Unit)?,
    currentScreen: String,
    feedNo: Int?
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                image = profileImage,
                modifier = Modifier
                    .size(35.dp)
                    .padding(start = 5.dp)
            )
            ProfileName(
                name = profileName,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
                fontStyle = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Normal
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onReactionClick != null && reactionIcon != null && reactionData != null && reactionTint != null && likeYN != null)
                CustomReaction(
                    modifier = Modifier,
                    iconResourceList = reactionIcon,
                    reactionData = reactionData,
                    onClickReaction = { onReactionClick(it) },
                    tintColor = reactionTint,
                    likeYN = likeYN
                )

            if (colorIcon != null) {
                colorIcon()
            }

            otherIcons?.forEach { (key, icon) ->
                when (key) {
                    "comment" -> {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "댓글창으로 이동하기",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    navController?.navigate(SecomiScreens.HomeDetailScreen.name + "/$feedNo") {
                                        launchSingleTop
                                        popUpTo(currentScreen)
                                    }
                                },
                            tint = CardIconsColor
                        )
                    }
                    "more" -> {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "설정창 열기",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    if (reportDialogCallAction != null) reportDialogCallAction(true)
                                },
                            tint = CardIconsColor
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CardContent(
    contentText: String,
    hashtagList: List<String>?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = contentText,
            modifier = Modifier.padding(
                start = 12.dp,
                end = 10.dp,
                top = 7.dp
            ),
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        if (!hashtagList.isNullOrEmpty()) {
            Row(Modifier.padding(start = 12.dp, end = 10.dp, top = 7.dp)) {
                hashtagList.forEachIndexed { index, tag ->
                    Text(
                        text = "#$tag",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = TagColor,
                            textAlign = TextAlign.Justify
                        )
                    )
                    if (index != hashtagList.lastIndex)
                        Spacer(modifier = Modifier.width(5.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        } else Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CardImageRow(
    imageList: List<String>,
    educationTitle: String?,
    educationTime: String?,
    currentScreen: String
) {
    val pagerState = rememberPagerState()
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) { page ->
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(imageList[page]),
                        contentDescription = "HomeFeed",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    if (educationTitle != null) Text(
                        text = educationTitle,
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                }
                if (educationTime != null) Text(
                    text = educationTime,
                    modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                    color = Color.White
                )
            }
        }

        if (currentScreen != SecomiScreens.EducationScreen.name)
            Surface(
                modifier = Modifier
                    .padding(2.dp)
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(50),
                color = IndicatorBlackTransparentColor
            ) {
                Text(
                    text = "${pagerState.currentPage + 1}/${imageList.size}",
                    modifier = Modifier.padding(
                        start = 17.dp,
                        end = 17.dp,
                        top = 7.dp,
                        bottom = 7.dp
                    ),
                    color = Color.White,
                    style = MaterialTheme.typography.caption
                )
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomLoginInputTextField(
    modifier: Modifier,
    inputEvent: (String) -> Unit,
    focusState: Boolean,
    color: Color = LoginLabelColor,
    label: String,
    isFocus: () -> Unit,
    keyboardOptions: KeyboardOptions
) {
    var text by remember {
        mutableStateOf("")
    }
    var labelPositionX by remember {
        mutableStateOf(0.dp)
    }
    var labelPositionY by remember {
        mutableStateOf(0.dp)
    }

    if (text.isNotEmpty()) {
        labelPositionX = (-2).dp
        labelPositionY = (-20).dp
    } else {
        labelPositionX = 0.dp
        labelPositionY = 0.dp
    }

    val xOffsetAnimation: Dp by animateDpAsState(
        if (!focusState) labelPositionX else (-2).dp
    )
    val yOffsetAnimation: Dp by animateDpAsState(
        if (!focusState) labelPositionY else (-20).dp
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            inputEvent(it)
            isFocus()
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        textStyle = TextStyle(
            LoginEmailInputColor
        ),
        singleLine = true,
        visualTransformation = if (label == "비밀번호" || label == "비밀번호 확인") PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            inputEvent(text)
            keyboardController?.hide()
        })
    ) { innerTextField ->
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .absoluteOffset(x = xOffsetAnimation, y = yOffsetAnimation),
                style = MaterialTheme.typography.caption,
                color = color
            )
            Column {
                innerTextField()
                Divider(
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth(),
                    color = if (focusState) LoginButtonColor else PlaceholderColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomReportDialog(
    reportAction: (String) -> Unit,
    dismissAction: (Boolean) -> Unit
) {
    val reportOptions =
        listOf("음란성 게시물", "폭력적 또는 불쾌한 게시물", "스팸 게시물", "사생활 침해/개인정보 유출 게시물", "불법적인 게시물")
    var selectedOption by remember {
        mutableStateOf("")
    }
    var selectedOptionToCode = "00"

    Dialog(
        onDismissRequest = { dismissAction(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(5),
            elevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    ) {}
                    Text(
                        text = "신고하기",
                        modifier = Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "확인",
                        modifier = Modifier.clickable {
                            when (selectedOption) {
                                reportOptions[0] -> selectedOptionToCode = "10"
                                reportOptions[1] -> selectedOptionToCode = "20"
                                reportOptions[2] -> selectedOptionToCode = "30"
                                reportOptions[3] -> selectedOptionToCode = "40"
                                reportOptions[4] -> selectedOptionToCode = "50"
                            }
                            reportAction(selectedOptionToCode)
                        },
                        color = BottomSheetCheckColor,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box {
                    reportOptions.forEachIndexed { index, reportOption ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = (index * 35).dp)
                                .clickable {
                                    selectedOption = reportOption
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            RadioButton(
                                selected = selectedOption == reportOption,
                                onClick = {
                                    selectedOption = reportOption
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = LoginButtonColor
                                )
                            )
                            Text(
                                text = reportOption
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ReportedFeed(
    feedNo: Int,
    reportCancelAction: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.clickable {
                reportCancelAction(feedNo)
            },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "신고 처리된 게시물입니다.",
                modifier = Modifier.padding(top = 25.dp),
                fontWeight = FontWeight.Bold, fontSize = 15.sp
            )
            Text(
                text = "실행 취소",
                modifier = Modifier.padding(top = 15.dp, bottom = 25.dp),
                color = ReportedNotificationColor
            )
        }
    }
}

fun showShortToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLongToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}