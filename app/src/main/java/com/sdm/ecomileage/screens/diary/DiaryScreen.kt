package com.sdm.ecomileage.screens.diary

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
import com.sdm.ecomileage.ui.theme.UnableUploadButtonColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DiaryScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    educationNo: Int?,
) {
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    if (educationNo == null)
        Text(text = "잘못된 접근입니다.")
    else
        DiaryScaffold(navController, educationNo)
}

@Composable
private fun DiaryScaffold(
    navController: NavController,
    educationNo: Int,
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
    var isShowBackAlert by remember { mutableStateOf(false) }

    BackHandler {
        isShowBackAlert = true
    }

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
                Surface(shape = RoundedCornerShape(5)) {
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
                    scope.launch(Dispatchers.IO) {
                        if (inputComment.value.length < 100) {
                            withContext(Dispatchers.Main) {
                                showShortToastMessage(context, "소감일기를 100자 이상 작성해주세요.")
                            }
                        } else educationViewModel.postDiary(educationNo, inputComment.value)
                            .let {
                                if (it.data?.code == 200) {
                                    showLongToastMessage(
                                        context,
                                        "소감일기 제출이 완료되었습니다.\n마일리지가 적립됩니다."
                                    )
                                    navController.navigate(SecomiScreens.EducationScreen.name) {
                                        launchSingleTop
                                    }
                                } else {
                                    showLongToastMessage(
                                        context,
                                        "소감일기 제출에 실패했습니다. ${it.data?.message}"
                                    )
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

    if (isShowBackAlert)
        Dialog(onDismissRequest = { isShowBackAlert = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                shape = RoundedCornerShape(5)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "정말 취소하고 돌아가시겠습니까?",
                        modifier = Modifier.padding(top = 10.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "소감일기를 적지 않으면\n포인트 획득이 불가능합니다.", textAlign = TextAlign.Center)
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = UnableUploadButtonColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "취소하고 나가기")
                    }
                }
            }
        }
}