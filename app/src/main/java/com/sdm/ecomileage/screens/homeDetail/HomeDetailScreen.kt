package com.sdm.ecomileage.screens.homeDetail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.ui.graphics.SolidColor
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
import com.sdm.ecomileage.components.ProfileImage
import com.sdm.ecomileage.components.ProfileName
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.data.HomeDetailCommentData
import com.sdm.ecomileage.model.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.comment.commentInfo.response.MainComment
import com.sdm.ecomileage.model.comment.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.comment.mainFeed.response.PostInfo
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.Constants
import com.sdm.ecomileage.utils.loginedUserId
import com.sdm.ecomileage.utils.uuidSample
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    userid = loginedUserId,
                    feedNo = feedNo
                )
            }
        }
    }.value



    when {
        feedNo == null -> {
            Text(text = "잘못된 접근입니다.")
        }

        mainFeedData.loading == true || commentInfoData.loading == true -> CircularProgressIndicator()
        mainFeedData.data?.result?.postInfo != null && commentInfoData.data?.result?.mainComment != null
        ->
            HomeDetailScaffold(
                navController,
                feedNo,
                commentViewModel,
                mainFeedData.data!!.result.postInfo,
                commentInfoData.data!!.result.mainComment,
                commentInfoData
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeDetailScaffold(
    navController: NavController,
    feedNo: Int,
    commentViewModel: HomeDetailViewModel,
    postInfo: PostInfo,
    mainComment: List<MainComment>,
    commentInfoData: DataOrException<CommentInfoResponse, Boolean, Exception>
) {
    val homeDetailCommentData = HomeDetailCommentData
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val localComment = SnapshotStateList<MainComment>()
    mainComment.forEach {
        localComment.add(it)
    }


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
                        //Todo : ProfileName 고치기
                        HomeDetailContent(
                            Modifier,
                            "${Constants.BASE_IMAGE_URL}${postInfo.profileimg}",
                            postInfo.userName,
                            postInfo.feedcontent,
                            postInfo.hashtags
                        )
                    }
                }
            }
        },
        bottomBar = {
            HomeDetailBottomCommentBar { comment ->
                scope.launch {
                    commentViewModel.postNewComment(
                        uuid = uuidSample,
                        feedNo = feedNo,
                        commentContent = comment
                    )
                    keyboardController?.hide()
                    commentInfoData.loading = true

                    // Todo : Proto Datastore 적용하면 profileImg, userName 바꾸기
                    localComment.add(
                        MainComment(
                            commentsno = 0,
                            parentcommentsno = 0,
                            photo = "",
                            profileimg = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
                            title = comment,
                            userId = "",
                            userName = "아이유는뉘집아이유"
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
                Row {
                    HomeDetailContent(
                        image = "${Constants.BASE_IMAGE_URL}${data.profileimg}",
                        name = data.userName,
                        text = data.title
                    )
                }
                if (index == homeDetailCommentData.lastIndex)
                    Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
private fun HomeDetailContent(
    modifier: Modifier = Modifier,
    image: String,
    name: String,
    text: String,
    tag: List<String>? = null,
) {
    Row(
        modifier = Modifier
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        ProfileImage(
            image = image,
            modifier = Modifier.size(45.dp),
            borderStroke = BorderStroke(0.dp, Color.Transparent)
        )
        Spacer(modifier = Modifier.width(5.dp))
        HomeDetailFormat(name = name, text = text, tagList = tag)
    }
}

@Composable
private fun HomeDetailFormat(
    name: String,
    text: String,
    tagList: List<String>?,
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
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 5.5.dp, top = 3.dp),
            style = MaterialTheme.typography.body2
        )

        LazyRow {
            if (tagList != null)
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