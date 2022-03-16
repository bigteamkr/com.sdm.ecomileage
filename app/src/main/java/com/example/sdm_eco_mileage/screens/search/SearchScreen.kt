package com.example.sdm_eco_mileage.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.CardContent
import com.example.sdm_eco_mileage.data.HomeScrollColumnViewData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.*
import com.example.sdm_eco_mileage.utils.Constants
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun SearchScreen(navController: NavController, systemUiController: SystemUiController) {
    systemUiController.setStatusBarColor(Color.White)


    SearchScaffold()
}

@Composable
private fun SearchScaffold() {
    val searchElement = remember {
        mutableStateOf("")
    }

    val isFocusOnSearch = remember {
        mutableStateOf(true)
    }

    val selectedZone = remember {
        mutableStateOf("")
    }
    val selectedFilter = remember {
        mutableStateOf("")
    }

    val filterFontSize = 15.sp

    val sampleHistory = listOf("장바구니", "나무", "화초키우기", "분리수거", "라벨분리법")

    Scaffold(
        topBar = { SearchScreenTopBar() }
    ) {
        Column {
            if (isFocusOnSearch.value) {
                SearchHistoryColumn(sampleHistory)
            }

            SearchFilterRow(
                filterFontSize,
                selectedZone,
                selectedFilter,
                onSelectZone = { selectedZone.value = it },
                onSelectFilter = { selectedFilter.value = it }
            )

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
    fontSize: TextUnit,
    selectedZone: MutableState<String>,
    selectedFilter: MutableState<String>,
    onSelectZone: (String) -> Unit,
    onSelectFilter: (String) -> Unit
) {
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
            Row() {
                Text(
                    text = "동네별",
                    modifier = Modifier.clickable {
                        onSelectZone("동네별")
                    },
                    style = TextStyle(
                        fontSize = fontSize,
                        color = if (selectedZone.value == "동네별") TagColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "학교별",
                    modifier = Modifier.clickable {
                        onSelectZone("학교별")
                    },
                    style = TextStyle(
                        fontSize = fontSize,
                        color = if (selectedZone.value == "학교별") TagColor else PlaceholderColor
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
                        fontSize = fontSize,
                        color = if (selectedFilter.value == "인기") indicatorSelectedColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "사용자",
                    modifier = Modifier.clickable {
                        onSelectFilter("사용자")
                    },
                    style = TextStyle(
                        fontSize = fontSize,
                        color = if (selectedFilter.value == "사용자") indicatorSelectedColor else PlaceholderColor
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "태그",
                    modifier = Modifier.clickable {
                        onSelectFilter("태그")
                    },
                    style = TextStyle(
                        fontSize = fontSize,
                        color = if (selectedFilter.value == "태그") indicatorSelectedColor else PlaceholderColor
                    )
                )
            }
        }
    }
}

@Composable
private fun SearchScreenTopBar() {
    val searchData = remember {
        mutableStateOf("")
    }

    TopAppBar(
        modifier = Modifier.padding(5.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        SearchInputField(
            fontSize = 18.sp,
            placeholderText = "검색",
            valueState = searchData,
            onAction = KeyboardActions(onDone = {

            })
        )
    }
}


@Composable
private fun SearchInputField(
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
            value = valueState.value,
            onValueChange = {
                valueState.value = it
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
                    if (valueState.value.isEmpty())
                        Text(
                            text = placeholderText,
                            modifier = Modifier.padding(start = 30.dp),
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
                            contentDescription = "",
                            tint = SearchIconColor
                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        innerTextField()
                    }
                }
            }
        }
    }
}
