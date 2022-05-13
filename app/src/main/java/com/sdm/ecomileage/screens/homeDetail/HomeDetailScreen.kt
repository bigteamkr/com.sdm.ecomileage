package com.sdm.ecomileage.screens.homeDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.data.AppSettings
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.data.HomeDetailCommentData
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.MainComment
import com.sdm.ecomileage.model.homedetail.loginUser.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.response.PostInfo
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.CommentReportOptions
import com.sdm.ecomileage.utils.currentUUIDUtil
import com.sdm.ecomileage.utils.dataStore
import com.sdm.ecomileage.utils.loginedUserIdUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeDetailScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    feedNo: Int?,
    commentViewModel: HomeDetailViewModel = hiltViewModel()
) {
    // Todo : rememberSaveable 로 전부 다 바꾸기
    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val appSettings = context.dataStore.data.collectAsState(initial = AppSettings())
    val uuid = appSettings.value.uuid

    val mainFeedData = produceState<DataOrException<MainFeedResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        if (feedNo != null) value = commentViewModel.getMainFeed(feedNo = feedNo)
    }.value

    val commentInfoData = produceState<DataOrException<CommentInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        scope.launch(context = Dispatchers.IO) {
            if (feedNo != null) {
                value = commentViewModel.getCommentInfo(
                    userid = loginedUserIdUtil,
                    feedNo = feedNo
                )
            }
        }
    }.value

    val loginUserInfoData =
        produceState<DataOrException<AppMemberInfoResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            scope.launch(context = Dispatchers.IO) {
                value = commentViewModel.getLoginUserInfo(uuid)
            }
        }.value


    when {
        feedNo == null -> {
            Text(text = "잘못된 접근입니다.")
        }

        mainFeedData.loading == true || commentInfoData.loading == true || loginUserInfoData.loading == true -> CircularProgressIndicator()
        mainFeedData.data?.result?.postInfo != null && commentInfoData.data?.result?.mainComment != null && loginUserInfoData.data?.result != null
        ->
            HomeDetailScaffold(
                navController,
                feedNo,
                mainFeedData.data!!.result.postInfo,
                commentInfoData.data!!.result.mainComment,
                commentInfoData,
                loginUserInfoData
            )
        else -> {
            Log.d(
                "HomeDetail",
                "HomeDetailScreen: ${mainFeedData.e}/ ${mainFeedData.data?.code} ${mainFeedData.data?.message}"
            )
            Log.d(
                "HomeDetail",
                "HomeDetailScreen: ${commentInfoData.e} / ${commentInfoData.data?.code} ${commentInfoData.data?.message}"
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeDetailScaffold(
    navController: NavController,
    feedNo: Int,
    postInfo: PostInfo,
    mainComment: List<MainComment>,
    commentInfoData: DataOrException<CommentInfoResponse, Boolean, Exception>,
    loginUserInfoData: DataOrException<AppMemberInfoResponse, Boolean, Exception>,
    commentViewModel: HomeDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val homeDetailCommentData = HomeDetailCommentData
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val localComment = SnapshotStateList<MainComment>()
    mainComment.forEach {
        localComment.add(it)
    }

    Log.d("comment", "HomeDetailScaffold: ${commentInfoData.data?.result?.mainComment?.size}")

    var reportDialogVisible by remember {
        mutableStateOf(false)
    }

    var reportTargetCommentNo by remember {
        mutableStateOf<Int?>(null)
    }

    var isCurrentFeedReporting by remember {
        mutableStateOf(false)
    }
    var reportListValue: String?

    var reportType = ""
    var reportContent = ""

    Scaffold(
        topBar = {
            Surface(
                elevation = 1.dp
            ) {
                Column {
                    SecomiTopAppBar(
                        title = "댓글",
                        navigationIcon = painterResource(id = R.drawable.ic_back_arrow),
                        navController = navController,
                        currentScreen = SecomiScreens.HomeDetailScreen.name,
                        backgroundColor = TopBarColor
                    )


                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) {
                        HomeDetailContent(
                            Modifier,
                            postInfo.userid,
                            postInfo.profileimg,
                            postInfo.userName,
                            postInfo.feedcontent,
                            postInfo.hashtags,
                            navController
                        )
                    }
                }
            }
        },
        bottomBar = {
            HomeDetailBottomCommentBar { comment ->
                scope.launch {
                    withContext(Dispatchers.IO) {
                        commentViewModel.postNewComment(
                            uuid = currentUUIDUtil,
                            feedNo = feedNo,
                            commentContent = comment
                        )
                    }
                    keyboardController?.hide()
                    commentInfoData.loading = true

                    localComment.add(
                        MainComment(
                            commentsno = 0,
                            parentcommentsno = 0,
                            photo = "",
                            profileimg = loginUserInfoData.data!!.result.userImg,
                            title = comment,
                            userId = loginUserInfoData.data!!.result.userId,
                            userName = loginUserInfoData.data!!.result.userName,
                            reportyn = false
                        )
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {

            itemsIndexed(localComment) { index, data ->
                isCurrentFeedReporting = commentViewModel.getReportingCommentValueFromKey(index)

                if (!data.reportyn)
                    HomeDetailContent(
                        userId = data.userId,
                        image = data.profileimg,
                        name = data.userName,
                        text = data.title,
                        navController = navController,
                        cancelReportAction = {
                            commentViewModel.deleteReportingComment(index)
                            isCurrentFeedReporting =
                                commentViewModel.getReportingCommentValueFromKey(index)

                            scope.launch(Dispatchers.IO) {
                                commentViewModel.postNewReportComment(
                                    feedsno = feedNo,
                                    commentsno = data.commentsno,
                                    reportType = reportType,
                                    reportContent = reportContent,
                                    reportyn = false
                                )
                            }
                        },
                        isItCommentFeed = true,
                        isThisReported = isCurrentFeedReporting,
                        onReportVisible = {
                            reportDialogVisible = true
                            reportTargetCommentNo = data.commentsno
                        }
                    )

                if (index == homeDetailCommentData.lastIndex)
                    Spacer(modifier = Modifier.height(50.dp))

                if (reportDialogVisible)
                    CustomReportDialog(
                        title = "${data.userName}님의 댓글을 신고",
                        reportAction = { selectedOptionToCode, reportDescription ->
                            if (selectedOptionToCode == "00") showShortToastMessage(
                                context,
                                "신고 사유를 선택해주세요."
                            )

                            commentViewModel.addReportingComment(index)
                            scope.launch(Dispatchers.IO) {
                                commentViewModel.postNewReportComment(
                                    feedsno = feedNo,
                                    commentsno = data.commentsno,
                                    reportType = selectedOptionToCode,
                                    reportContent = reportDescription.toString(),
                                    reportyn = true
                                )
                            }
                            reportType = selectedOptionToCode
                            reportContent = reportDescription.toString()
                            reportDialogVisible = false
                        }, dismissAction = {
                            reportDialogVisible = false
                        }, reportOptions = CommentReportOptions
                    )
            }
        }

    }
}

@Composable
private fun HomeDetailContent(
    modifier: Modifier = Modifier,
    userId: String,
    image: String,
    name: String,
    text: String,
    tag: List<String>? = null,
    navController: NavController,
    cancelReportAction: () -> Unit = {},
    isItCommentFeed: Boolean = false,
    onReportVisible: (() -> Unit)? = null,
    isThisReported: Boolean = false
) {
    Row(
        modifier = Modifier
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            if (isThisReported) {
                BlockedProfileImage()
                Spacer(modifier = Modifier.width(5.dp))
                HomeDetailFormat(
                    name = "***",
                    text = "신고 처리 된 댓글입니다.",
                    tagList = null,
                    isThisReported = true,
                    cancelReportAction = cancelReportAction
                )
            } else {
                ProfileImage(
                    userId = userId,
                    image = image,
                    modifier = Modifier.size(45.dp),
                    borderStroke = BorderStroke(0.dp, Color.Transparent),
                    navController = navController
                )
                Spacer(modifier = Modifier.width(5.dp))
                HomeDetailFormat(name = name, text = text, tagList = tag)
            }
        }

        if (isItCommentFeed && !isThisReported)
            Surface(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(20.dp)
                    .clickable {
                        if (onReportVisible != null) onReportVisible()
                    },
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "내 댓글 수정하기 혹은 남의 글 신고하기 버튼",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(UnselectedButtonColor)
                )
            }
    }
}

@Composable
private fun HomeDetailFormat(
    name: String,
    text: String,
    tagList: List<String>?,
    cancelReportAction: () -> Unit = {},
    isThisReported: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        ProfileName(
            name = name,
            modifier = Modifier.padding(start = 5.dp),
            textAlign = TextAlign.Start,
            fontStyle = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = if (isThisReported) ReportedContentColor else Color.Black
        )
        Row {
            Text(
                text = text,
                modifier = Modifier.padding(start = 5.5.dp, top = 3.dp),
                style = MaterialTheme.typography.body2,
                color = if (isThisReported) ReportedContentColor else Color.Black
            )
            if (isThisReported)
                Text(
                    text = "실행 취소",
                    modifier = Modifier
                        .padding(start = 30.dp, top = 1.dp)
                        .clickable {
                            cancelReportAction()
                        },
                    color = LoginButtonColor,
                    fontSize = 15.sp
                )
        }

        LazyRow {
            if (tagList != null && !isThisReported)
                items(tagList) { tag ->
                    Text(
                        text = "#$tag",
                        modifier = Modifier.padding(start = 5.5.dp, top = 4.dp),
                        style = MaterialTheme.typography.body2,
                        color = TagColor
                    )
                }
        }

    }
}


@OptIn(
    ExperimentalComposeUiApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
private fun HomeDetailBottomCommentBar(
    submitComment: (String) -> Unit
) {
    //Todo : viewModel 로 데이터 핸들링할 것
    val comment = rememberSaveable {
        mutableStateOf("")
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    BottomAppBar(
        backgroundColor = CommentBackgroundColor,
        modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 5.dp, end = 2.dp)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            InputField(
                valueState = comment,
                fontSize = 13.sp,
                placeholderText = "댓글을 입력하세요",
                isSingleLine = true,
                onAction = KeyboardActions(onDone = {
                    if (comment.value != "") submitComment(comment.value)
                    comment.value = ""
                })
            )
            Button(
                onClick = {
                    if (comment.value != "") submitComment(comment.value)
                    comment.value = ""
                },
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .height(37.5.dp),
                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SendButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "보내기", style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    fontSize: TextUnit,
    placeholderText: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions
) {
    Surface(
        modifier = Modifier
            .height(37.dp)
            .fillMaxWidth(0.78f),
        shape = RoundedCornerShape(
            topStartPercent = 50,
            bottomStartPercent = 50,
            topEndPercent = 0,
            bottomEndPercent = 0
        ),
        color = Color.White,
        elevation = 0.dp,
    ) {
        BasicTextField(
            value = valueState.value,
            onValueChange = {
                valueState.value = it
            },
            modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 8.dp, end = 15.dp),
            singleLine = isSingleLine,
            cursorBrush = SolidColor(Color.Black),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction
        ) { innerTextField ->
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (valueState.value.isEmpty())
                        Text(
                            text = placeholderText, style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    innerTextField()
                }
            }
        }
    }
}