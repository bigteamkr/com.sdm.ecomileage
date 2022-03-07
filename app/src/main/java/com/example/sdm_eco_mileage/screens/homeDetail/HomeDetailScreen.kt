package com.example.sdm_eco_mileage.screens.homeDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.components.ProfileImage
import com.example.sdm_eco_mileage.components.ProfileName
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.HomeDetailCommentData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.TagColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor

//Todo : Home Detail Screen 시작하기
@Composable
fun HomeDetailScreen(navController: NavController) {
    val homeDetailCommentData = HomeDetailCommentData

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "댓글",
                navController = navController,
                currentScreen = SecomiScreens.HomeDetailScreen.name,
                backgroundColor = TopBarColor
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            HomeDetailContent(
                homeDetailCommentData[0].image,
                homeDetailCommentData[0].name,
                homeDetailCommentData[0].text,
                "#컵홀더 #diy #예쁘다 #녹색생활"
            )
            Divider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
            LazyColumn {
                itemsIndexed(homeDetailCommentData) { index, data ->
                    // Todo : index 1 개 건너띄는거 Sample 볼라카는거니 조심하쇼
                    if (index == 1)
                        Box {}
                    Row() {
                        HomeDetailContent(image = data.image, name = data.name, text = data.text)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeDetailContent(
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

@Preview
@Composable
private fun HomeDetailBottomSearchBar(){
    //Todo : viewModel 로 데이터 핸들링할 것
    val comment = remember{
        mutableStateOf("")
    }
    // Todo : Button 에 JetTrivia Color 채우기 활용할 것
    BottomAppBar(
        backgroundColor = Color.LightGray
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd
        ){
            InputField(valueState = comment, enabled = true, isSingleLine = true)
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
            ) {

            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        shape = RoundedCornerShape(30.dp)
    )
}