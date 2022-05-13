package com.sdm.ecomileage.screens.settings

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appMemberInfo.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.appMemberInfo.response.Result
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.loginedUserIdUtil


@Composable
fun SettingsScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    appSettingsViewModel: AppSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    val appSettingsInfo = produceState<DataOrException<AppMemberInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = appSettingsViewModel.getAppMemberInfo()
        Log.d("AppSettingsScreen", "SettingsScreen: $value")
    }.value

    if (appSettingsInfo.loading == true)
        CircularProgressIndicator(color = LoginButtonColor)
    else if (appSettingsInfo.data?.result != null) {
        appSettingsInfo.data?.code?.let {
            if (it == 200) return@let
            else {
                AutoLoginLogic(
                    isLoading = { isLoading = true },
                    navController = navController,
                    context = context,
                    screen = SecomiScreens.SettingsScreen.name
                )
            }
        }
        AppSettingsScaffold(navController, appSettingsInfo.data?.result!!)
    }

    if (isLoading) CircularProgressIndicator(color = LoginButtonColor)
}

@Composable
private fun AppSettingsScaffold(navController: NavController, result: Result) {
    val context = LocalContext.current

    var pointSaveType by remember { mutableStateOf(0) }
    var profileImgBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val imageCameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) profileImgBitmap = takenPhoto
            else showShortToastMessage(context, "카메라 촬영을 취소하셨습니다.")
        }


    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "환경설정",
                navigationIcon = painterResource(id = R.drawable.ic_back_arrow),
                currentScreen = SecomiScreens.SettingsScreen.name,
                navController = navController
            )
        },
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.SettingsScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier.background(SettingsBackgroundColor),
            verticalArrangement = Arrangement.Center
        ) {
            SettingsProfile(navController)
            Spacer(modifier = Modifier.height(10.dp))
            SettingsProfileName(result.userName)
            Spacer(modifier = Modifier.height(5.dp))
            MileageRow(result.userPoint.toString())
            UserInformationRow(result.userDept, result.userTown, "")
            ConnectedSocialAccountRow(id = "seodamoon@naver.com")
            MileageTypeRow(pointSaveType) { pointSaveType = it }

            var schoolAlarm by remember { mutableStateOf(result.schoolAlarmAgree) }
            var townAlarm by remember { mutableStateOf(result.townAlarmAgree) }
            var eventAlarm by remember { mutableStateOf(result.eventAlarmAgree) }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(10),
                elevation = 2.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "알림 선택",
                        modifier = Modifier.padding(top = 10.dp),
                        color = UnableUploadButtonColor
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "우리 학교 알림 받기")
                        Switch(
                            checked = schoolAlarm,
                            onCheckedChange = { schoolAlarm = !schoolAlarm },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = LoginButtonColor
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "우리 동 알림 받기")
                        Switch(
                            checked = townAlarm,
                            onCheckedChange = { townAlarm = !townAlarm },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = LoginButtonColor
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 75.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "이벤트 알림 받기")
                        Switch(
                            checked = eventAlarm,
                            onCheckedChange = { eventAlarm = !eventAlarm },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = LoginButtonColor
                            )
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun MileageTypeRow(pointSaveType: Int, click: (Int) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(10),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "랭킹 마일리지 선택", color = UnableUploadButtonColor)
            Row {
                Row {
                    Surface(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(15.dp)
                            .clickable { click(0) },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(3.dp)
                                .size(5.dp),
                            shape = CircleShape,
                            color = if (pointSaveType == 0) LoginButtonColor else Color.White
                        ) {}
                    }
                    Text(text = "우리 학교", modifier = Modifier.padding(start = 5.dp))
                }
                Spacer(modifier = Modifier.width(95.dp))
                Row {
                    Surface(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(15.dp)
                            .clickable { click(0) },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(3.dp)
                                .size(5.dp),
                            shape = CircleShape,
                            color = if (pointSaveType == 1) LoginButtonColor else Color.White
                        ) {}
                    }
                    Text(text = "우리 동", modifier = Modifier.padding(start = 5.dp))
                }
            }
        }
    }
}


@Composable
private fun ConnectedSocialAccountRow(
    type: String = "",
    id: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "연결된 계정", color = UnableUploadButtonColor)
            Text(text = id)
        }
    }
}

@Composable
private fun UserInformationRow(
    school: String,
    address: String,
    phoneNumber: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(5),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = school, color = UnableUploadButtonColor)
                Text(text = address, color = UnableUploadButtonColor)
                Text(text = phoneNumber, color = UnableUploadButtonColor)
            }
            Column(
                modifier = Modifier
                    .padding(start = 117.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "해당 없음")
                Text(text = "서대문구 북가좌동")
                Text(text = "010-1234-5678")
            }
            Surface(
                modifier = Modifier
                    .width(70.dp)
                    .height(30.dp),
                color = Color.White
            ) {}
        }
    }
}

@Composable
private fun MileageRow(point: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "마일리지", color = UnableUploadButtonColor)
            Text(
                text = point,
                modifier = Modifier.padding(start = 80.dp)
            )
            Surface(
                modifier = Modifier
                    .width(70.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(30),
                color = MileageChangeButtonColor,
                contentColor = Color.White
            ) {
                Text(
                    text = "전환신청",
                    modifier = Modifier
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SettingsProfileName(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 5.sp
        )
    }
}

@Composable
private fun SettingsProfile(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .size(100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ProfileImage(
                modifier = Modifier.fillMaxSize(),
                userId = loginedUserIdUtil,
                image = "http://blog.jinbo.net/attach/615/200937431.jpg",
                navController = navController,
                isNonClickable = true
            )
            Image(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "프로필 사진 변경하기",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        role = Role.Tab
                    ) {

                    },
            )
        }
    }
}