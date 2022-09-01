package com.sdm.ecomileage.screens.settings

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiBottomBar
import com.sdm.ecomileage.components.SecomiMainFloatingActionButton
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.appMemberInfo.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.appMemberInfo.response.Result
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.loginRegister.AutoLoginLogic
import com.sdm.ecomileage.screens.loginRegister.LoginRegisterViewModel
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.bitmapToString
import com.sdm.ecomileage.utils.isAutoLoginUtil
import com.sdm.ecomileage.utils.setIsAutoLogin
import com.sdm.ecomileage.utils.setRefreshToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
                backgroundColor = TopBarColorOrigin,
                navigationIcon = painterResource(id = R.drawable.ic_back_arrow),
                currentScreen = SecomiScreens.SettingsScreen.name,
                navController = navController,
                contentColor = Color.White
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
            SettingsProfile(navController, result.userImg)
            Spacer(modifier = Modifier.height(10.dp))
            SettingsProfileName(result.userName)
            Spacer(modifier = Modifier.height(5.dp))
            LogOutRow(navController)
            UserInformationRow(result.userDept, result.userAddress, "")
            ConnectedSocialAccountRow(result.userSSOType, result.userSSOID)
            MileageTypeRow(pointSaveType) { pointSaveType = it }
            AlarmSettingRow(result)
        }
    }
}

@Composable
private fun AlarmSettingRow(result: Result) {
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
                            .clickable { click(1) },
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
            Row {
                when (type) {
                    "kakao" -> Image(
                        rememberImagePainter(R.drawable.ic_kakaotalk),
                        contentDescription = "연결된 소셜로그인은 카카오 입니다.",
                        modifier = Modifier.size(22.dp)
                    )
                    "naver" -> Image(
                        rememberImagePainter(R.drawable.ic_naver),
                        contentDescription = "연결된 소셜로그인은 네이버 입니다.",
                        modifier = Modifier.size(22.dp)
                    )
                    "google" -> Image(
                        rememberImagePainter(R.drawable.ic_google),
                        contentDescription = "연결된 소셜로그인은 구글 입니다.",
                        modifier = Modifier.size(22.dp)
                    )
                    "facebook" -> Image(
                        rememberImagePainter(R.drawable.ic_facebook),
                        contentDescription = "연결된 소셜로그인은 페이스북 입니다.",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(text = if (id == "") "-" else id)
            }
        }
    }
}

@Composable
private fun UserInformationRow(
    school: String,
    address: String,
    phoneNumber: String
) {
    var isEditing by remember { mutableStateOf(false) }
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
                Text(text = "학교", color = UnableUploadButtonColor)
                Text(text = "거주지", color = UnableUploadButtonColor)
                Text(text = "전화번호", color = UnableUploadButtonColor)
            }
            Column(
                modifier = Modifier
                    .padding(start = 117.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = school)
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        modifier = Modifier
                            .padding(
                                end = 5.dp
                            )
//                            .clickable { isEditing = true },
                        ,
                        tint = PlaceholderColor
                    )
                }
                Text(text = address)
                Text(text = phoneNumber)
            }
            Surface(
                modifier = Modifier
                    .width(70.dp)
                    .height(30.dp),
                color = Color.White
            ) {}
        }
    }

    if (isEditing) {
        Dialog(onDismissRequest = { isEditing = false }) {
            Surface(shape = RoundedCornerShape(20.dp)) {
                Column {
                    Text("")
                }
            }
        }
    }
}

@Composable
private fun LogOutRow(navController: NavController) {
    var isClicked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isClicked) {
        setIsAutoLogin(false)
        setRefreshToken("")
        isAutoLoginUtil = false
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .clickable {
                navController.navigate(SecomiScreens.LoginScreen.name + "/0") {
                    popUpTo(SecomiScreens.SettingsScreen.name) { inclusive = true }
                }
            },
        shape = RoundedCornerShape(10),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "", color = UnableUploadButtonColor)
            Row(
                modifier = Modifier.fillMaxWidth(0.57f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "로그아웃",
                    textAlign = TextAlign.Start,
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
private fun SettingsProfile(
    navController: NavController,
    image: String,
    appSettingsViewModel: AppSettingsViewModel = hiltViewModel(),
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var source: ImageDecoder.Source

    var dropdownOpen by remember {
        mutableStateOf(false)
    }

    var profileImgBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                imageUri = result.uriContent
                //Todo : API 연결하기

            } else {
                val exception = result.error
            }
        }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
//            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
//            imageCropLauncher.launch(cropOptions)
            imageUri = uri

            if (profileImgBitmap != null) {
                val image = bitmapToString(profileImgBitmap!!)
                scope.launch {
                    loginRegisterViewModel.putMemberUpdate(profileImg = image)
                }
            }
        }

    val imageCameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                profileImgBitmap = takenPhoto
                //Todo : API 연결하기

                val image = bitmapToString(profileImgBitmap!!)
                scope.launch {
                    loginRegisterViewModel.putMemberUpdate(profileImg = image)
                }

            } else showShortToastMessage(context, "카메라 촬영을 취소하셨습니다.")
        }

    LaunchedEffect(key1 = imageUri) {
        if (imageUri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                profileImgBitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

            } else {
                source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                profileImgBitmap = ImageDecoder.decodeBitmap(source)
            }
        }
    }


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
            Surface(
                shape = CircleShape
            ) {
                Image(
                    painter = rememberImagePainter(data = imageUri ?: image),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }
//            ProfileImage(
//                modifier = Modifier.fillMaxSize(),
//                userId = currentLoginedUserId,
//                image = image,
//                navController = navController,
//                isNonClickable = true
//            )

            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "프로필 사진 변경하기",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            dropdownOpen = true
                        }
                )
                DropdownMenu(
                    expanded = dropdownOpen,
                    onDismissRequest = { dropdownOpen = !dropdownOpen }) {
                    DropdownMenuItem(onClick = { imageCameraLauncher.launch() }) {
                        Text(text = "카메라에서 추가하기")
                    }
                    DropdownMenuItem(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text(text = "갤러리에서 추가하기")
                    }
                    if (profileImgBitmap != null)
                        DropdownMenuItem(onClick = {
                            profileImgBitmap = null
                            scope.launch(Dispatchers.IO) {
                                loginRegisterViewModel.putMemberUpdate()
                            }
                        }) {
                            Text(text = "기본사진으로 설정하기")
                        }
                }
            }
        }
    }


}