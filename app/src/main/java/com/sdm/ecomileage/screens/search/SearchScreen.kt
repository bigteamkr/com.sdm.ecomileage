package com.sdm.ecomileage.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.search.response.Feed
import com.sdm.ecomileage.model.search.response.SearchFeedResponse
import com.sdm.ecomileage.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    SearchScaffold(navController, searchViewModel)
}

@Composable
private fun SearchScaffold(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    var isFocusOnSearch by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }


    val sampleHistory = listOf("장바구니", "나무", "화초키우기", "분리수거", "라벨분리법")

    val scope = rememberCoroutineScope()
//    var searchedFeedResponse: DataOrException<SearchFeedResponse, Boolean, Exception> =
//        DataOrException(loading = false)
//
//    var searchedFeedData: List<Feed>? = searchedFeedResponse.data?.result?.feedList

    Scaffold(
        topBar = {
            SearchScreenTopBar(
                valueState = searchViewModel.searchText,
                valueHoist = { searchViewModel.onSearchTextChanged(it) }
            ) {
//                scope.launch {
//                    searchViewModel.getSearchFeedInfo().let {
//                        searchedFeedResponse = it
//                    }
//                }
            }
        }
    ) {
        Column {
            if (isFocusOnSearch) {
                SearchHistoryColumn(sampleHistory)
            }

            SearchFilterRow(
                searchViewModel.selectedZone,
                searchViewModel.selectedFilter,
                onSelectZone = { searchViewModel.onSelectedZone(it) },
                onSelectFilter = { searchViewModel.onSelectedFilter(it) }
            )


            //Todo : Search API 요소가 다름
//            LazyColumn {
//                items(searchedFeedData) { data ->
//                    CardContent(
//                        contentImageList = listOf(data.photo),
//                        contentText = data.feedcontent,
//                        profileImage = data.profileimg,
//                        profileName = data.userName,
//                        reactionIcon = listOf(
//                            R.drawable.ic_like_off,
//                            R.drawable.ic_like_on
//                        ),
//                        reactionData = data.likeCount,
//                        reactionTint = LikeColor,
//                        likeYN = data,
//                        onReactionClick = {
//                            scope.launch {
//                                homeViewModel.postFeedLike(data.feedsno, it)
//                            }
//                        },
//                        otherIcons = mapOf(
//                            "comment" to R.drawable.ic_comment,
//                            "more" to R.drawable.ic_more
//                        ),
//                        hashtagList = data.hashtags,
//                        navController = navController,
//                        feedNo = data.feedsno,
//                        currentScreen = SecomiScreens.HomeDetailScreen.name,
//                        destinationScreen = null
//                    )
//                    if (index == postListData.lastIndex)
//                        Spacer(modifier = Modifier.height(70.dp))
//                }
//            }


        }
    }
}

@Composable
private fun SearchHistoryColumn(sampleHistory: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(15.dp)
    ) {
        Text(
            text = "최근 검색어",
            style = TextStyle(
                color = PlaceholderColor,
                fontSize = 14.sp,
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn {
            items(sampleHistory) { history ->
                Text(
                    text = history,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.LightGray,
                    )
                )
                Divider(Modifier.padding(top = 5.dp, bottom = 5.dp))
            }
        }
    }
}

@Composable
private fun SearchFilterRow(
    selectedZone: String,
    selectedFilter: String,
    onSelectZone: (String) -> Unit,
    onSelectFilter: (String) -> Unit
) {
    val filterFontSize = 15.sp

    Column(
        modifier = Modifier
            .padding(5.dp)
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "동네별",
                    modifier = Modifier.clickable {
                        onSelectZone("동네별")
                    },
                    style = TextStyle(
                        fontSize = filterFontSize,
                        color = if (selectedZone == "동네별") TagColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "학교별",
                    modifier = Modifier.clickable {
                        onSelectZone("학교별")
                    },
                    style = TextStyle(
                        fontSize = filterFontSize,
                        color = if (selectedZone == "학교별") TagColor else PlaceholderColor
                    )
                )
            }
            Row(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = "인기",
                    modifier = Modifier.clickable {
                        onSelectFilter("인기")
                    },
                    style = TextStyle(
                        fontSize = filterFontSize,
                        color = if (selectedFilter == "인기") indicatorSelectedColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "사용자",
                    modifier = Modifier.clickable {
                        onSelectFilter("사용자")
                    },
                    style = TextStyle(
                        fontSize = filterFontSize,
                        color = if (selectedFilter == "사용자") indicatorSelectedColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "태그",
                    modifier = Modifier.clickable {
                        onSelectFilter("태그")
                    },
                    style = TextStyle(
                        fontSize = filterFontSize,
                        color = if (selectedFilter == "태그") indicatorSelectedColor else PlaceholderColor
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreenTopBar(
    valueState: String,
    valueHoist: (String) -> Unit,
    onAction: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        modifier = Modifier.padding(5.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        SearchInputField(
            fontSize = 18.sp,
            placeholderText = "검색",
            valueState = valueState,
            valueHoist = { valueHoist(it) },
            onAction = KeyboardActions(onDone = {
                keyboardController?.hide()
                onAction()
            })
        )
    }
}


@Composable
private fun SearchInputField(
    modifier: Modifier = Modifier,
    valueState: String,
    valueHoist: (String) -> Unit,
    fontSize: TextUnit,
    placeholderText: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions
) {
    Surface(
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStartPercent = 50,
            bottomStartPercent = 50,
            topEndPercent = 50,
            bottomEndPercent = 50
        ),
        color = CommentBackgroundColor,
        elevation = 0.dp,
    ) {
        BasicTextField(
            value = valueState,
            onValueChange = {
                valueHoist(it)
            },
            modifier = Modifier.padding(start = 15.dp, top = 5.dp, bottom = 8.dp, end = 15.dp),
            singleLine = isSingleLine,
            cursorBrush = SolidColor(Color.Black),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = fontSize
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
                    if (valueState.isEmpty())
                        Text(
                            text = placeholderText,
                            modifier = Modifier.padding(start = 30.dp, top = 1.dp),
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    Row(
                        modifier = Modifier.padding(top = 3.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색",
                            tint = SearchIconColor
                        )

                        Column(
                            modifier = Modifier.padding(start = 5.dp, top = 2.dp)
                        ) {
                            innerTextField()
                        }
                    }
                }
            }
        }
    }
}
