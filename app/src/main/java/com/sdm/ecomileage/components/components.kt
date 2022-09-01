package com.sdm.ecomileage.components

import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sdm.ecomileage.R
import com.sdm.ecomileage.model.registerPage.searchLocation.areaResponse.Search
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.homeAdd.ContentInputField
import com.sdm.ecomileage.screens.homeAdd.DotsIndicator
import com.sdm.ecomileage.screens.loginRegister.LoginRegisterViewModel
import com.sdm.ecomileage.screens.myPage.MyPageViewModel
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.currentLoginedUserId
import com.sdm.ecomileage.utils.currentUUIDUtil
import kotlinx.coroutines.launch

@Composable
fun SecomiTopAppBar(
    title: String,
    navigationIcon: Painter? = null,
    currentScreen: String,
    backgroundColor: List<Color> = TopBarColorWhite,
    actionIconsList: List<@Composable () -> Unit>? = null,
    navController: NavController,
    contentColor: Color = Color.Black,
    homeFriendList: (() -> Unit)? = null
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
        Column {
            Box(
                contentAlignment = Alignment.Center
            ) {
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
                                AppBarTitleText(
                                    title,
                                    Modifier.padding(start = 8.dp),
                                    contentColor,
                                    17.sp
                                )
                            }
                            SecomiScreens.EducationScreen.name -> {
                                AppBarTitleText(
                                    title,
                                    Modifier.padding(start = 8.dp),
                                    contentColor,
                                    17.sp
                                )
                            }
                            SecomiScreens.SelectTopicScreen.name -> {
                                AppBarTitleText(title, Modifier, contentColor, 17.sp)
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

                            SecomiScreens.SettingsScreen.name -> {
                                AppBarTitleText(
                                    title,
                                    Modifier.padding(end = 25.dp),
                                    contentColor,
                                    18.sp
                                )
                            }

                            SecomiScreens.MileageRanking.name -> {
                                AppBarTitleText(
                                    title,
                                    Modifier.padding(start = 67.dp),
                                    contentColor,
                                    18.sp
                                )
                            }

                            SecomiScreens.MyHistoryScreen.name -> {
                                AppBarTitleText(
                                    title,
                                    Modifier.padding(end = 25.dp),
                                    contentColor,
                                    18.sp
                                )
                            }

                            SecomiScreens.MileageChangeScreen.name -> {
                                AppBarTitleText(
                                    title = title,
                                    modifier = Modifier.padding(end = 25.dp),
                                    contentColor = contentColor,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }

                    // Right - Icons or empty
                    Row(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        actionIconsList?.forEachIndexed { index, it ->
                            it()
                            if (index != actionIconsList.lastIndex) {
                                Spacer(modifier = Modifier.width(15.dp))
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
        fontWeight = FontWeight.Normal,
        maxLines = 1,
        letterSpacing = 1.0.sp
    )
}

@Composable
fun SecomiMainFloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(SecomiScreens.SelectTopicScreen.name)
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
    textPadding: Dp = 45.dp,
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
            modifier = modifier,
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
                .padding(
                    end = textPadding
                ),
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.ExtraBold,
            color = tintColor,
            fontSize = 14.sp
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
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textData,
            modifier = Modifier
                .then(modifier),
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Normal,
            color = tintColor
        )

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = tintColor
        )

    }
}

@Composable
fun ProfileName(
    name: String?,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    fontStyle: TextStyle = TextStyle.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Black
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
        overflow = TextOverflow.Ellipsis,
        color = color,
        letterSpacing = letterSpacing
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImage(
    userId: String,
    image: String,
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(
        width = 0.dp,
        color = Color.LightGray
    ),
    navController: NavController,
    isNonClickable: Boolean = false
) {
    Surface(
        onClick = {
            if (!isNonClickable)
                navController.navigate(SecomiScreens.MyPageScreen.name + "/$userId") {
                    launchSingleTop
                }
        },
        modifier = modifier
            .size(55.dp),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BlockedProfileImage(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(
        width = 0.dp,
        color = Color.Transparent
    )
) {
    Surface(
        onClick = {},
        modifier = modifier
            .size(45.dp)
            .then(modifier),
        shape = CircleShape,
        border = borderStroke
    ) {
        Image(
            painter = painterResource(R.drawable.ic_blocked_account),
            contentDescription = "Profile",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MainFeedCardStructure(
    contentImageList: List<String>,
    contentText: String,
    profileImage: String,
    profileId: String,
    profileName: String,
    reactionIcon: List<Int>,
    reactionData: Int,
    reactionTint: Color,
    followYN: Boolean,
    likeYN: Boolean,
    reportYN: Boolean,
    onReactionClick: (Boolean) -> Unit,
    otherIcons: Map<String, Int>,
    commentCount: Int? = null,
    hashtagList: List<String>?,
    navController: NavController,
    feedNo: Int,
    isCurrentReportingFeedsNo: Boolean,
    deleteFeedAction: (() -> Unit)? = null,
    reportDialogCallAction: (Offset) -> Unit,
    reportingCancelAction: (Int) -> Unit,
    currentScreen: String,
    destinationScreen: String?,
    showIndicator: Boolean = true,
    openBigImage: (Int) -> Unit = {}
) {
    var isReportingCard by remember { mutableStateOf(isCurrentReportingFeedsNo) }

    LaunchedEffect(key1 = isCurrentReportingFeedsNo) {
        isReportingCard = isCurrentReportingFeedsNo
    }

    when {
        reportYN -> Box { }
        isReportingCard -> {
            AnimatedVisibility(
                visible = isReportingCard,
                enter = fadeIn()
            ) {
                ReportedFeed(feedNo) {
                    reportingCancelAction(it)
                }
            }
        }
        else -> {
            MainCardFeed(
                contentImageList,
                profileImage,
                profileId,
                profileName,
                null,
                null,
                reactionIcon,
                reactionData,
                onReactionClick,
                reactionTint,
                likeYN,
                followYN,
                null,
                otherIcons,
                commentCount,
                navController,
                deleteFeedAction,
                reportDialogCallAction,
                currentScreen,
                feedNo,
                contentText,
                hashtagList,
                destinationScreen,
                showIndicator,
                openBigImage = openBigImage
            )
        }
    }
}

@Composable
fun MainCardFeed(
    contentImageList: List<String>,
    profileImage: String,
    profileId: String,
    profileName: String,
    educationTitle: String? = null,
    educationTime: String? = null,
    reactionIcon: List<Int>?,
    reactionData: Int?,
    onReactionClick: ((Boolean) -> Unit)?,
    reactionTint: Color?,
    likeYN: Boolean? = null,
    followYN: Boolean? = null,
    colorIcon: @Composable() (() -> Unit)?,
    otherIcons: Map<String, Int>?,
    commentCount: Int? = null,
    navController: NavController,
    deleteFeedAction: (() -> Unit)? = null,
    reportDialogCallAction: ((Offset) -> Unit)?,
    currentScreen: String,
    feedNo: Int,
    contentText: String?,
    hashtagList: List<String>?,
    destinationScreen: String?,
    showIndicator: Boolean,
    isOnEducation: Boolean = false,
    sizeModifier: Modifier = Modifier,
    openBigImage: (Int) -> Unit = {},
    educationClick: Modifier = Modifier,
) {
    val heightModifier = if (currentScreen == SecomiScreens.EducationScreen.name) 230.dp else 300.dp

    val navigatingModifier = if (destinationScreen != null) Modifier.clickable {
        navController.navigate(destinationScreen) {
            launchSingleTop
            popUpTo(currentScreen)
        }
    } else Modifier

    Card(
        modifier = Modifier
            .padding(10.dp)
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .then(navigatingModifier)
            .then(sizeModifier)
            .then(educationClick),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightModifier)
            ) {
                CardImageRow(
                    contentImageList,
                    currentScreen,
                    educationTitle,
                    educationTime,
                    showIndicator = showIndicator,
                    openBigImage = openBigImage
                )
            }
            CardContent(
                contentText,
                hashtagList,
                profileId,
                feedNo,
                followYN,
                deleteFeedAction,
                reportDialogCallAction,
                currentScreen
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .padding(top = 13.dp)
                    .fillMaxWidth()
                    .height(0.3.dp)
            )
            CardWriterInformation(
                profileImage,
                profileId,
                profileName,
                reactionIcon,
                reactionData,
                onReactionClick,
                reactionTint,
                likeYN,
                followYN,
                colorIcon,
                otherIcons,
                commentCount,
                navController,
                deleteFeedAction,
                reportDialogCallAction,
                currentScreen,
                feedNo,
                isOnEducation
            )
        }
    }
}

@Composable
fun CardWriterInformation(
    profileImage: String,
    profileId: String,
    profileName: String,
    reactionIcon: List<Int>?,
    reactionData: Int?,
    onReactionClick: ((Boolean) -> Unit)?,
    reactionTint: Color?,
    likeYN: Boolean?,
    followYN: Boolean? = null,
    colorIcon: (@Composable () -> Unit)?,
    otherIcons: (Map<String, Int>)?,
    commentCount: Int? = null,
    navController: NavController,
    deleteFeedAction: (() -> Unit)? = null,
    reportDialogCallAction: ((Offset) -> Unit)?,
    currentScreen: String,
    feedNo: Int?,
    isOnEducation: Boolean = false,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var expandedDropdown by remember {
        mutableStateOf(false)
    }
    var followInfo by remember {
        mutableStateOf(followYN)
    }

    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                userId = profileId,
                image = profileImage,
                modifier = Modifier
                    .size(45.dp)
                    .padding(
                        start = 5.dp,
                        top = 5.dp
                    ),
                navController = navController,
                isNonClickable = isOnEducation
            )
            ProfileName(
                name = profileName,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp),
                fontStyle = MaterialTheme.typography.body2,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.2.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onReactionClick != null && reactionIcon != null && reactionData != null && reactionTint != null && likeYN != null)
                CustomReaction(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 5.dp),
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
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = "댓글창으로 이동하기",
                                modifier = Modifier
                                    .size(29.dp)
                                    .padding(start = 5.dp, bottom = 2.dp)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        feedNo?.let {
                                            navController.navigate(SecomiScreens.HomeDetailScreen.name + "/$feedNo") {
                                                launchSingleTop
                                                popUpTo(currentScreen)
                                            }
                                        }
                                    },
                                tint = CardIconsColor
                            )
                            Text(
                                text = commentCount?.toString() ?: "0",
                                modifier = Modifier
                                    .padding(
                                        end = 35.dp
                                    ),
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.ExtraBold,
                                color = CardIconsColor,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun CardContent(
    contentText: String?,
    hashtagList: List<String>?,
    profileId: String,
    feedNo: Int?,
    followYN: Boolean? = null,
    deleteFeedAction: (() -> Unit)? = null,
    reportDialogCallAction: ((Offset) -> Unit)? = null,
    currentScreen: String,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    var expandedDropdown by remember {
        mutableStateOf(false)
    }
    var followInfo by remember {
        mutableStateOf(followYN)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        if (contentText != null)
            Text(
                text = contentText,
                modifier = Modifier.padding(
                    start = 5.dp,
                    end = 55.dp,
                    top = 20.dp
                ),
                color = CardContentColor,
                style = MaterialTheme.typography.body2,
                fontSize = 15.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 1.15.sp,
                softWrap = true
            )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!hashtagList.isNullOrEmpty()) {
                Row(Modifier.padding(start = 5.dp, top = 7.dp)) {
                    hashtagList.forEachIndexed { index, tag ->
                        val modifier =
                            if (index != hashtagList.lastIndex) Modifier.padding(end = 7.dp) else Modifier
                        Text(
                            text = "#$tag",
                            modifier = modifier,
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = CardContentColor,
                                textAlign = TextAlign.Justify
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            } else Spacer(modifier = Modifier.height(20.dp))

            if (currentScreen != SecomiScreens.EducationScreen.name)
                Box {
                    Surface(
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_new_burger),
                            contentDescription = "설정창 열기",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 3.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    expandedDropdown = true
                                },
                            tint = CardIconsColor
                        )
                    }
                    DropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = !expandedDropdown }
                    ) {
                        when (profileId) {
                            currentLoginedUserId -> {
                                DropdownMenuItem(onClick = {
                                    if (deleteFeedAction != null && feedNo != null) {
                                        scope.launch {
                                            myPageViewModel.deleteMyFeed(
                                                feedNo
                                            ).let {
                                                if (it.data?.code == 200) deleteFeedAction()
                                                else {
                                                    Log.d(
                                                        "component",
                                                        "CardWriterInformation: ${it.data?.message} ${it.data?.code}"
                                                    )
                                                    showShortToastMessage(
                                                        context,
                                                        "${it.data?.message}"
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        Log.d("component", "CardWriterInformation: $feedNo")
                                        showShortToastMessage(context, "오류가 발생하였습니다.")
                                    }
                                }) {
                                    Text(text = "삭제하기")
                                }
                            }
                            else -> {
                                if (followInfo == false)
                                    DropdownMenuItem(onClick = {
                                        scope.launch {
                                            myPageViewModel.putNewFollowInfo(
                                                currentUUIDUtil,
                                                profileId,
                                                true
                                            ).let {
                                                if (it.data?.code == 200) followInfo = true
                                                else showShortToastMessage(
                                                    context,
                                                    "잠시 후 다시 시도해주세요."
                                                )
                                            }
                                        }
                                        expandedDropdown = false
                                    }) {
                                        Text(text = "팔로우하기")
                                    }
                                else if (followInfo == true)
                                    DropdownMenuItem(onClick = {
                                        scope.launch {
                                            myPageViewModel.putNewFollowInfo(
                                                currentUUIDUtil,
                                                profileId,
                                                false
                                            ).let {
                                                if (it.data?.code == 200) followInfo = false
                                                else showShortToastMessage(
                                                    context,
                                                    "잠시 후 다시 시도해주세요."
                                                )
                                            }
                                        }
                                        expandedDropdown = false
                                    }) {
                                        Text(text = "팔로우 취소하기")
                                    }
                                DropdownMenuItem(onClick = {
                                    expandedDropdown = false
                                    reportDialogCallAction?.invoke(Offset.Zero)
                                }) {
                                    Text(text = "신고하기")
                                }
                            }
                        }
                    }
                }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CardImageRow(
    imageList: List<String>,
    currentScreen: String,
    educationTitle: String? = null,
    educationTime: String? = null,
    indicatorSurfaceSize: Dp? = null,
    indicatorTextSize: TextUnit? = null,
    showIndicator: Boolean = true,
    openBigImage: (Int) -> Unit = {},
    radius: Int = 11,
) {
    val pagerState = rememberPagerState()

    Surface(
        shape = RoundedCornerShape(radius.dp)
    ) {
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
                            contentDescription = "FeedImageList",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        if (educationTitle == null)
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        openBigImage(pagerState.currentPage)
                                    },
                                color = Color.Transparent
                            ) {}
                        else
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = ThumbnailAlpha
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = educationTitle,
                                            style = MaterialTheme.typography.h1,
                                            color = Color.White,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            softWrap = true,
                                            letterSpacing = 1.2.sp
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 20.dp, bottom = 20.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_play_circle),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                    }
                    if (educationTime != null)
                        Text(
                            text = educationTime,
                            modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                            color = Color.White
                        )
                }
            }

            if (showIndicator)
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
                        fontSize = indicatorTextSize ?: TextUnit.Unspecified,
                        color = Color.White,
                        style = MaterialTheme.typography.caption
                    )
                }
        }
    }
}

@Composable
fun SecomiBottomBar(
    navController: NavController,
    currentScreen: String,
    scrollState: LazyListState? = null
) {
    BottomAppBar(
        backgroundColor = Color.White,
        cutoutShape = CircleShape,
        elevation = 5.dp
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
                currentBottomButton = SecomiScreens.HomeScreen.name,
                iconResource = homeIcon,
                label = "HOME",
                description = "Home Navigate Button",
                scrollState = scrollState
            )

            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                0.sp,
                labelStyle,
                currentBottomButton = SecomiScreens.EducationScreen.name,
                iconResource = educationIcon,
                label = "EDUCATION",
                description = "Education Navigate Button"
            )


            Spacer(modifier = Modifier.width(70.dp))


            BottomBarItem(
                selectedIconName,
                navController,
                iconModifier,
                labelModifier,
                letterSpacing,
                labelStyle,
                currentBottomButton = SecomiScreens.EventScreen.name,
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
                currentBottomButton = SecomiScreens.MyPageScreen.name,
                iconResource = myPageIcon,
                label = "MY PAGE",
                description = "My Page Navigate Button"
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    currentScreen: MutableState<String>,
    navController: NavController,
    iconModifier: Modifier,
    labelModifier: Modifier,
    letterSpacing: TextUnit,
    labelStyle: TextStyle,
    currentBottomButton: String,
    iconResource: MutableState<Int>,
    label: String,
    description: String,
    scrollState: LazyListState? = null
) {
    val route =
        if (currentBottomButton == SecomiScreens.MyPageScreen.name) "$currentBottomButton/myPage"
        else currentBottomButton
    val scope = rememberCoroutineScope()

    BottomNavigationItem(
        selected = currentScreen.value == currentBottomButton,
        onClick = {
            if (currentScreen.value != currentBottomButton) {
                navController.navigate(route) {
                    launchSingleTop
                    popUpTo(currentScreen.value) { inclusive = true }
                }
                currentScreen.value = currentBottomButton
            } else if (currentScreen.value == currentBottomButton)
                scope.launch { scrollState?.animateScrollToItem(0, 1) }
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
                fontSize = if (label == "EDUCATION") 8.5.sp else 9.5.sp,
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
    enabled: Boolean = true,
    label: String,
    isFocus: () -> Unit,
    keyboardOptions: KeyboardOptions,
    defaultText: String? = null
) {
    var text by remember {
        mutableStateOf(defaultText ?: "")
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
        labelPositionX
    )
    val yOffsetAnimation: Dp by animateDpAsState(
        labelPositionY
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
        enabled = enabled,
        singleLine = true,
        visualTransformation = if (label == "주민등록번호 뒷자리" || label == "비밀번호 (영문 숫자 포함 9자 이상)" || label == "비밀번호 확인 (영문 숫자 포함 9자 이상)" || label == "비밀번호") PasswordVisualTransformation() else VisualTransformation.None,
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


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomReportDialog(
    title: String,
    reportAction: (String, String?) -> Unit,
    dismissAction: (Boolean) -> Unit,
    reportOptions: List<String>,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var selectedOption by remember {
        mutableStateOf("")
    }
    var selectedOptionToCode = "00"
    var reportDetailDescription = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = { dismissAction(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .height((configuration.screenHeightDp * 0.5).dp),
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
                        text = title,
                        modifier = Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "확인",
                        modifier = Modifier.clickable {
                            reportAction(selectedOptionToCode, reportDetailDescription.value)
                        },
                        color = BottomSheetCheckColor,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column() {
                    Box {
                        reportOptions.forEachIndexed { index, reportOption ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = (index * 35).dp)
                                    .clickable {
                                        selectedOption = reportOption
                                        selectedOptionToCode = index.toString() + "0"
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RadioButton(
                                    selected = selectedOption == reportOption,
                                    onClick = {
                                        selectedOption = reportOption
                                        selectedOptionToCode = index.toString() + "0"
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
                    Surface(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        border = BorderStroke(1.dp, BorderColor)
                    ) {
                        ContentInputField(
                            inputComment = reportDetailDescription,
                            keyboardAction = { keyboardController?.hide() },
                            placeholderText = "상세사유(선택사항)",
                            modifier = Modifier.height(60.dp)
                        )
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterBottomSheetMain(
    isArea: Boolean = true,
    areaInput: List<Search>,
    schoolInput: List<com.sdm.ecomileage.model.registerPage.searchLocation.schoolResponse.Search>,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel(),
    hideSheet: (Int, String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var input by remember {
        mutableStateOf("")
    }

    val areaList = SnapshotStateList<Search>()
    areaInput.forEach {
        areaList.add(it)
    }
    val schoolList =
        SnapshotStateList<com.sdm.ecomileage.model.registerPage.searchLocation.schoolResponse.Search>()
    schoolInput.forEach {
        schoolList.add(it)
    }
    // 위에껀 areaSearch, 밑에껀 schoolSearch
    // 같은 이름의 Search 이지만 패키지로 다르게 들어가고 있음.

    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .height(3.dp)
                    .width(50.dp),
                shape = RoundedCornerShape(100),
                color = BottomSheetDividerColor
            ) {}

            Text(
                text = if (isArea) "동네 검색" else "학교 검색",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = ProfileDescriptionColor
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .padding(start = 15.dp, end = 10.dp)
                        .fillMaxWidth(0.75f)
                        .height(ButtonDefaults.MinHeight),
                    textStyle = TextStyle(color = ProfileDescriptionColor)
                ) {
                    Surface(
                        shape = RoundedCornerShape(5),
                        border = BorderStroke(1.dp, LoginLabelColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(start = 10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            it()
                        }
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            if (isArea) loginRegisterViewModel.getSearchLocalArea(input).data?.result?.searchList?.let {
                                areaList.clear()
                                areaList.addAll(
                                    it
                                )
                            }
                            else loginRegisterViewModel.getSearchLocalSchool(input).data?.result?.searchList?.let {
                                schoolList.clear()
                                schoolList.addAll(
                                    it
                                )
                            }
                        }
                        keyboardController?.hide()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LoginButtonColor
                    )
                ) {
                    Text(text = "검색", color = Color.White)
                }
            }
            Spacer(Modifier.height(15.dp))

            Divider(
                modifier = Modifier
                    .height(3.dp),
                color = BottomSheetSearchBackgroundColor
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BottomSheetSearchBackgroundColor),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (areaList.isEmpty() && schoolList.isEmpty())
                    item {
                        Text(
                            text = if (isArea) "동네를 검색해주세요." else "학교를 검색해주세요.",
                            color = BottomSheetGreyColor
                        )
                    }

                items(areaList) { area ->
                    Surface(
                        modifier = Modifier.clickable {
                            hideSheet(
                                (area.areaId).toInt(),
                                "${area.sidoname} ${area.sggname} ${area.areaName}"
                            )
                        }
                    ) { RegisterBottomSheetLocationItem(name = "${area.sidoname} ${area.sggname} ${area.areaName}") }
                }
                items(schoolList) { school ->
                    Surface(
                        Modifier.clickable {
                            hideSheet(
                                school.schoolId,
                                "${school.schoolName}"
                            )
                        }
                    ) {
                        RegisterBottomSheetLocationItem(
                            name = school.schoolName,
                            address = "${school.sidoname} ${school.sggname}"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RegisterBottomSheetLocationItem(
    name: String,
    address: String? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_option),
                contentDescription = "위치 아이콘",
                modifier = Modifier.padding(20.dp),
                tint = LoginButtonColor
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = name, color = ProfileDescriptionColor)
                Spacer(modifier = Modifier.height(5.dp))
                address?.let {
                    Text(
                        text = address,
                        color = RegisterGreyColor,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.height(5.dp),
            color = BottomSheetSearchBackgroundColor
        )
    }
}

@Preview
@Composable
fun MileageSwipeButton() {
    var isOnLeft by remember { mutableStateOf(true) }
    val thumbLineGap = 6
    val width = 73.dp
    val height = 24.dp
    val backgroundColor =
        if (isOnLeft) MileageSwipeButtonSchoolColor else MileageSwipeButtonTownColor
    val firstText = "학교"
    val secondText = "동네"
    val thumbLinePosition = 3.7.dp

    val thumbLinePositionAnimation = animateFloatAsState(
        targetValue = if (isOnLeft) with(LocalDensity.current) { 2.5.dp.toPx() }
        else with(LocalDensity.current) { ((width / 7) * 4).toPx() + 1.dp.toPx() }
    )

    val animatePosition = animateFloatAsState(
        targetValue = if (isOnLeft) with(LocalDensity.current) { 2.dp.toPx() }
        else with(LocalDensity.current) { ((width / 7) * 4).toPx() - 1.dp.toPx() }
    )

    Canvas(
        modifier = Modifier
            .padding(1.dp)
            .size(width, height)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { isOnLeft = !isOnLeft }
                )
            }
    ) {
        drawRoundRect(
            color = backgroundColor,
            size = Size(width.toPx(), height.toPx()),
            cornerRadius = CornerRadius(50f, 50f)
        )

        drawContext.canvas.nativeCanvas.apply {
            if (isOnLeft)
                drawText(
                    firstText,
                    ((width / 2) + 4.dp).toPx(),
                    ((height / 2) + 4.5.dp).toPx(),
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 14.dp.toPx()
                        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    }
                )
            else drawText(
                secondText,
                (5.dp).toPx(),
                ((height / 2) + 4.5.dp).toPx(),
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 14.dp.toPx()
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
            )
        }

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(animatePosition.value, 2.5.dp.toPx()),
            size = Size(((width / 7) * 3).toPx(), (height - 5.dp).toPx()),
            cornerRadius = CornerRadius(80f, 80f)
        )

        for (i in 1..4) {
            drawLine(
                color = BottomSheetDividerColor,
                start = Offset(
                    thumbLinePositionAnimation.value + (i * thumbLineGap).dp.toPx(),
                    (height / 3).toPx()
                ),
                end = Offset(
                    thumbLinePositionAnimation.value + (i * thumbLineGap).dp.toPx(),
                    ((height / 3) * 2).toPx()
                ),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@Composable
fun customBigImageDialog(
    imageList: List<String>,
    configuration: Configuration,
    currentIndex: Int,
    closeAction: (Boolean) -> Unit
) {
    val pagerState = rememberPagerState()
    var isOpen by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        pagerState.scrollToPage(currentIndex)
    }

    Dialog(
        onDismissRequest = { closeAction(!isOpen) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .size(
                    width = configuration.screenWidthDp.dp,
                    height = configuration.screenHeightDp.dp
                ),
            color = Color.Black
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "",
                    modifier = Modifier
                        .padding(20.dp)
                        .size(24.dp)
                        .clickable {
                            closeAction(!isOpen)
                        },
                    tint = Color.White
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        count = imageList.size,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f),
                        itemSpacing = 0.dp
                    ) { page ->
                        Image(
                            painter = rememberImagePainter(imageList[page]),
                            contentDescription = "FeedImageList",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Surface(
                        modifier = Modifier.padding(top = 40.dp),
                        color = Color.Black
                    ) {
                        DotsIndicator(
                            totalDots = imageList.size,
                            selectedIndex = pagerState.currentPage,
                            selectedColor = Color.White,
                            unSelectedColor = BottomSheetGreyColor,
                            dotsSize = 13,
                            dotsSpacing = 10
                        )
                    }
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

