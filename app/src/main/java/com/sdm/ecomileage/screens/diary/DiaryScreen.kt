package com.sdm.ecomileage.screens.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.components.showLongToastMessage
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.education.EducationViewModel
import com.sdm.ecomileage.screens.homeAdd.ContentInputField
import com.sdm.ecomileage.ui.theme.ButtonColor
import com.sdm.ecomileage.ui.theme.RankingTitleColor
import kotlinx.coroutines.launch

@Composable
fun DiaryScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    educationNo: Int?,
    educationViewModel: EducationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val inputComment = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardReaction = {}
    val placeholderText = "50자 이상 작성하여 제출해주세요."

    if (educationNo == null)
        Text(text = "잘못된 접근입니다.")
    else
        Scaffold(
            topBar = {
                SecomiTopAppBar(
                    title = "소감일기 쓰기",
                    navigationIcon = painterResource(id = R.drawable.ic_out),
                    currentScreen = SecomiScreens.DiaryScreen.name,
                    navController = navController,
                    backgroundColor = listOf(Color.White, Color.White),
                    contentColor = RankingTitleColor
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(shape = RoundedCornerShape(15)) {
                        Image(
                            painter = rememberImagePainter("http://blog.jinbo.net/attach/615/200937431.jpg"),
                            contentDescription = "샘플이라네",
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.25f)
                        )
                    }
                    Column(modifier = Modifier.padding(10.dp)) {
                        ContentInputField(
                            inputComment = inputComment,
                            keyboardAction = { keyboardReaction() },
                            placeholderText = placeholderText,
                            modifier = Modifier.height((configuration.screenHeightDp * 0.4).dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            educationViewModel.postDiary(educationNo, inputComment.value).let {
                                if (it.data?.code == 200){
                                    showLongToastMessage(context, "소감일기 제출이 완료되었습니다.\n마일리지가 적립됩니다.")
                                    navController.navigate(SecomiScreens.EducationScreen.name){
                                        launchSingleTop
                                    }
                                } else {
                                    showLongToastMessage(context, "소감일기 제출에 실패했습니다. ${it.data?.message}")
                                }
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ButtonColor
                    )
                ) {
                    Text(text = "소감일기 제출하기", color = Color.White)
                }
            }
        }
}