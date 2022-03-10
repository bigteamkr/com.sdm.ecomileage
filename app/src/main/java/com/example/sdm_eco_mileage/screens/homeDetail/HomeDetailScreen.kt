package com.example.sdm_eco_mileage.screens.homeDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.ProfileImage
import com.example.sdm_eco_mileage.components.ProfileName
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.HomeDetailCommentData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.CommentBackgroundColor
import com.example.sdm_eco_mileage.ui.theme.SendButtonColor
import com.example.sdm_eco_mileage.ui.theme.TagColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor

//Todo : Home Detail Screen 시작하기
@Composable
fun HomeDetailScreen(navController: NavController) {
    val homeDetailCommentData = HomeDetailCommentData
    val focusRequester = remember { FocusRequester() }

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
                            .focusRequester(focusRequester = focusRequester)
                    ) {
                        HomeDetailContent(
                            Modifier,
                            homeDetailCommentData[0].image,
                            homeDetailCommentData[0].name,
                            homeDetailCommentData[0].text,
                            "#컵홀더 #diy #예쁘다 #녹색생활"
                        )
                    }
                }

            }

        },
        bottomBar = {
            HomeDetailBottomCommentBar()
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            itemsIndexed(homeDetailCommentData) { index, data ->
                // Todo : index 1 개 건너띄는거 Sample 볼라카는거니 조심하쇼
                if (index == 0)
                    Box {}
                else Row {
                    HomeDetailContent(image = data.image, name = data.name, text = data.text)
                }
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
    tag: String? = null,
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
        HomeDetailFormat(name = name, text = text, tag = tag)
    }
}

@Composable
private fun HomeDetailFormat(
    name: String,
    text: String,
    tag: String?,
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
            fontWeight = FontWeight.Bold
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 5.5.dp, top = 3.dp),
            style = MaterialTheme.typography.body2
        )
        if (tag != null)
            Text(
                text = tag,
                modifier = Modifier.padding(start = 5.5.dp, top = 4.dp),
                style = MaterialTheme.typography.body2,
                color = TagColor
            )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun HomeDetailBottomCommentBar() {
    //Todo : viewModel 로 데이터 핸들링할 것
    val comment = rememberSaveable {
        mutableStateOf("")
    }

    BottomAppBar(
        backgroundColor = CommentBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 5.dp, end = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            InputField(
                valueState = comment,
                fontSize = 13.sp,
                placeholderText = "댓글을 입력하세요",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions(onDone = {

                })
            )
            Button(
                onClick = {

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
    enabled: Boolean,
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
            modifier = Modifier.padding(start = 15.dp, top = 5.dp, bottom = 8.dp, end = 15.dp),
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
                    if (valueState.value.isNullOrEmpty())
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