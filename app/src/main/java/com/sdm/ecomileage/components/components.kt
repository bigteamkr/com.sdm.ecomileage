package com.sdm.ecomileage.components

import android.content.Context
import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sdm.ecomileage.R
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.Constants

@Composable
fun SecomiTopAppBar(
    title: String = "베타테스터",
    navigationIcon: Painter? = null,
    currentScreen: String = SecomiScreens.HomeScreen.name,
    backgroundColor: List<Color> = TopBarColor,
    actionIconsList: Map<String, Painter>? = null,
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
fun CustomIconText(
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
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        IconToggleButton(
            checked = isChecked,
            onCheckedChange = {
                isChecked = !isChecked
                onClickReaction(isChecked)
                _reactionData = if (isChecked) _reactionData + 1 else _reactionData - 1
            },
            modifier = Modifier.size(22.dp),
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
fun CardContent(
    contentImageList: List<String>,
    contentText: String,
    profileImage: String,
    profileName: String,
    reactionIcon: List<Int>,
    reactionData: Int,
    reactionTint: Color,
    likeYN: Boolean,
    onReactionClick: (Boolean) -> Unit,
    otherIcons: Map<String, Int>,
    hashtagList: List<String>?,
    navController: NavController,
    feedNo: Int,
    currentScreen: String,
    destinationScreen: String?
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .height(460.dp)
            .clickable {
                if (destinationScreen != null) navController.navigate(destinationScreen) {
                    launchSingleTop
                    popUpTo(currentScreen)
                }
            },
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
                    .fillMaxHeight(0.77f)
            ) {
                CardImageRow(contentImageList)
            }
            CardWriterInformation(
                profileImage,
                profileName,
                reactionIcon,
                reactionData,
                onReactionClick,
                reactionTint,
                likeYN,
                otherIcons,
                navController,
                currentScreen,
                feedNo
            )
            CardContent(contentText, hashtagList)
        }
    }
}

@Composable
private fun CardWriterInformation(
    profileImage: String,
    profileName: String,
    reactionIcon: List<Int>,
    reactionData: Int,
    onReactionClick: (Boolean) -> Unit,
    reactionTint: Color,
    likeYN: Boolean,
    otherIcons: Map<String, Int>,
    navController: NavController,
    currentScreen: String,
    feedNo: Int
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            ProfileImage(
                image = "${Constants.BASE_IMAGE_URL}$profileImage",
                modifier = Modifier
                    .size(35.dp)
                    .padding(start = 5.dp)
            )
            ProfileName(
                name = profileName,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 5.dp),
                fontStyle = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Normal,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 1.dp)
        ) {
            CustomIconText(
                iconResourceList = reactionIcon,
                reactionData = reactionData,
                onClickReaction = { onReactionClick(it) },
                tintColor = reactionTint,
                likeYN = likeYN
            )

            otherIcons.forEach { (key, icon) ->
                when (key) {
                    "comment" -> {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "댓글창으로 이동하기",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    navController.navigate(SecomiScreens.HomeDetailScreen.name + "/$feedNo") {
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
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CardImageRow(
    imageList: List<String>
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
            Surface(
                Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter("${Constants.BASE_IMAGE_URL}${imageList[page]}"),
                    contentDescription = "HomeFeed",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Surface(
            modifier = Modifier
                .padding(2.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(50),
            color = IndicatorBlackTransparentColor
        ) {
            Text(
                text = "${pagerState.currentPage + 1}/${imageList.size}",
                modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = 7.dp, bottom = 7.dp),
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
fun CustomInputTextField(
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

@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheet() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val reportOptions =
        listOf("음란성 게시물", "폭력적 또는 불쾌한 게시물", "스팸 게시물", "사생활 침해/개인정보 유출 게시물", "불법적인 게시물")
    var selectedOption by remember {
        mutableStateOf("")
    }

    BottomSheetScaffold(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .height(400.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {}
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Divider(
                modifier = Modifier
                    .width(40.dp)
                    .padding(top = 10.dp)
                    .background(BottomSheetDividerColor),
                thickness = 2.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                    color = BottomSheetCheckColor,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            reportOptions.forEach { reportOption ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        text = reportOption,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                selectedOption = reportOption
                            }
                    )
                }
            }
        }
    }
}

fun showShortToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLongToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}