package com.sdm.ecomileage.screens.loginRegister

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.accompanist.systemuicontroller.SystemUiController
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.data.AppSettings
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    type: Int,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var isLoading by remember {
        mutableStateOf(false)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White
        )
    }

    BackHandler {
        doubleBackForFinish(context)
    }

    if (isAutoLoginUtil) {
        AutoLoginLogic(
            loginRegisterViewModel,
            { isLoading = it },
            navController,
            context,
            SecomiScreens.HomeScreen.name
        )
    }

    var tabIndex by remember { mutableStateOf(type) }
    val tabTitles = listOf("로그인", "회원가입")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.White,
            contentColor = SelectedTabColor
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    modifier = Modifier.background(Color.White),
                    onClick = { tabIndex = index },
                    text = { Text(text = title) },
                    selectedContentColor = SelectedTabColor,
                    unselectedContentColor = PlaceholderColor
                )
            }
        }

        when (tabIndex) {
            0 -> LoginScaffold(
                navController
            ) {
                tabIndex = 1
            }
            1 -> RegisterScaffold(
                navController
            ) {
                tabIndex = 0
            }
        }
    }
}

@Composable
fun AutoLoginLogic(
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel(),
    isLoading: (Boolean) -> Unit,
    navController: NavController,
    context: Context,
    screen: String
) {
    LaunchedEffect(key1 = true) {
        loginRegisterViewModel.getAppRequestToken(
            currentUUIDUtil,
            refreshTokenUtil
        ).let {
            if (it.loading == true) isLoading(true)
            Log.d("AUTO Login", "LoginScreen: $currentUUIDUtil")
            Log.d("AUTO Login", "LoginScreen: $refreshTokenUtil")

            it.data?.tokenInfo?.let { response ->
                accessTokenUtil = response.accessToken
                setRefreshToken(response.refreshToken)
                loginedUserIdUtil = lastLoginedUserIdUtil

                navController.navigate(screen) {
                    popUpTo(SecomiScreens.LoginScreen.name) { inclusive = true }
                }
            } ?: withContext(Dispatchers.Main) {
                showShortToastMessage(context, "로그인을 다시 시도해주세요.")
            }
        }
    }
}

@Composable
fun RegisterScaffold(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel(),
    backToLogin: () -> Unit
) {
    //Todo : WA 리팩토링할게 천지뺴까리에요 :) 이거 전부 함수안에 넣으세요 :)
    var isFirstTermAgree by remember {
        mutableStateOf(false)
    }
    var isSecondTermAgree by remember {
        mutableStateOf(false)
    }
    var isThirdTermAgree by remember {
        mutableStateOf(false)
    }
    var isTermsAgree by remember {
        mutableStateOf(false)
    }

    var showTerms by remember {
        mutableStateOf(true)
    }

    var isRegisterDataInputStep by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = isFirstTermAgree, key2 = isSecondTermAgree, key3 = isThirdTermAgree) {
        isTermsAgree = isFirstTermAgree && isSecondTermAgree && isThirdTermAgree
    }

    if (showTerms) TermsOfServicePage(
        isFirstTermAgree,
        isFirstTermEvent = { isFirstTermAgree = it },
        isSecondTermAgree,
        isSecondTermEvent = { isSecondTermAgree = it },
        isThirdTermAgree,
        isThirdTermEvent = { isThirdTermAgree = it },
        isTermsAgree,
        termsCheck = { isTermsAgree = it }
    ) {
        showTerms = it
    }
    else if (isRegisterDataInputStep) {
        RegisterPage(backToLogin) {
            isRegisterDataInputStep = it
        }
    } else if (!isRegisterDataInputStep) {
        ProfileSubmitPage(navController, loginRegisterViewModel)
    }
}

@Composable
private fun ProfileSubmitPage(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var profileImgBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var source: ImageDecoder.Source

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                imageUri = result.uriContent
            } else {
                val exception = result.error
            }
        }
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
            imageCropLauncher.launch(cropOptions)
        }

    val imageCameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) profileImgBitmap = takenPhoto
            else showShortToastMessage(context, "카메라 촬영을 취소하셨습니다.")
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



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(150.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = rememberImagePainter(if (profileImgBitmap == null) loginRegisterViewModel.defaultProfile!!.toBitmap() else profileImgBitmap),
                    contentDescription = "프로필 변경 아이콘",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "당신의 프로필 사진을\n등록해보세요",
                fontSize = 15.sp,
                color = ProfileDescriptionColor,
                textAlign = TextAlign.Center,
            )

            Row(
                modifier = Modifier
                    .padding(start = 110.dp, end = 110.dp, top = 70.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "카메라를 통해 프로필 사진 업로드",
                        modifier = Modifier
                            .size(43.dp)
                            .clickable {
                                imageCameraLauncher.launch()
                            }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "카메라",
                        modifier = Modifier.padding(start = 1.dp),
                        fontSize = 15.sp,
                        color = ProfileIconColor
                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "갤러리를 통해 프로필 사진 업로드",
                        modifier = Modifier
                            .size(43.dp)
                            .clickable {
                                scope.launch {
                                    imagePickerLauncher.launch("image/*")
                                }
                            }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "갤러리",
                        modifier = Modifier.padding(start = 1.dp),
                        fontSize = 15.sp,
                        color = ProfileIconColor
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Button(
                onClick = {
                    if (profileImgBitmap != null) {
                        scope.launch {
                            loginRegisterViewModel.putMemberUpdate(
                                profileImg = bitmapToString(profileImgBitmap!!)
                            ).let {
                                Log.d(
                                    "putMemberUpdate",
                                    "ProfileSubmitPage: ${bitmapToString(profileImgBitmap!!)}"
                                )
                                Log.d("putMemberUpdate", "ProfileSubmitPage: ${it.data?.message}")
                                navController.navigate(SecomiScreens.HomeScreen.name) {
                                    launchSingleTop
                                }
                            }
                        }
                    } else
                        showShortToastMessage(context, "프로필을 다시 업로드해주세요.")
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LoginButtonColor,
                    disabledBackgroundColor = PlaceholderColor
                ),
                enabled = profileImgBitmap != null
            ) {
                Text(text = "완료", color = Color.White)
            }
            Button(
                onClick = {
                    navController.navigate(SecomiScreens.HomeScreen.name) {
                        launchSingleTop
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(text = "건너뛰기", color = LoginButtonColor)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
private fun RegisterPage(
    backToLogin: () -> Unit,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel(),
    onRegisterButtonClick: (Boolean) -> Unit
) {
    var userName by remember {
        mutableStateOf("")
    }
    var isNameInputFocus by remember {
        mutableStateOf(false)
    }
    var emailHead by remember {
        mutableStateOf("")
    }
    var emailTail by remember {
        mutableStateOf("")
    }
    var isEmailInputFocus by remember {
        mutableStateOf(false)
    }
    var passwordFirst by remember {
        mutableStateOf("")
    }
    var isPasswordFirstInputFocus by remember {
        mutableStateOf(false)
    }
    var passwordSecond by remember {
        mutableStateOf("")
    }
    var isPasswordSecondInputFocus by remember {
        mutableStateOf(false)
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var isPhoneNumberInputFocus by remember {
        mutableStateOf(false)
    }
    var isCheck by remember {
        mutableStateOf(false)
    }
    var validationCheck by remember {
        mutableStateOf(false)
    }

    var addressId = ""
    var address by remember {
        mutableStateOf("")
    }
    var deptId = ""
    var dept by remember {
        mutableStateOf("")
    }

    var isOrganizationNull by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    val bottomSheetScaffoldState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val appSettings = context.dataStore.data.collectAsState(initial = AppSettings())
    var isAreaForBottomSheet by remember { mutableStateOf(true) }

    BackHandler {
        if (bottomSheetScaffoldState.isVisible) scope.launch { bottomSheetScaffoldState.hide() }
        else backToLogin()
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        sheetContent = {
            RegisterBottomSheetMain(isAreaForBottomSheet) { id, name ->
                if (isAreaForBottomSheet) {
                    addressId = id.toString()
                    address = name
                } else {
                    deptId = id.toString()
                    dept = name
                }
                scope.launch { bottomSheetScaffoldState.hide() }
            }
        },
        sheetShape = RoundedCornerShape(topStartPercent = 3, topEndPercent = 3),
        sheetBackgroundColor = PlaceholderColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = AnnotatedString(
                        "*",
                        spanStyle = SpanStyle(
                            fontSize = 14.sp,
                            color = Color.Red
                        )
                    ) + AnnotatedString(
                        "기본정보",
                        spanStyle = SpanStyle(
                            color = PlaceholderColor
                        )
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                CustomLoginInputTextField(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .focusRequester(focusRequester)
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    inputEvent = {
                        userName = it
                        scope.launch {
                            validationCheck =
                                userName.isNotEmpty() && emailHead.isNotEmpty() && emailTail.isNotEmpty() &&
                                        passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty()
                        }
                    },
                    focusState = isNameInputFocus,
                    label = "성함",
                    isFocus = {
                        isNameInputFocus = true
                        isEmailInputFocus = false
                        isPasswordFirstInputFocus = false
                        isPasswordSecondInputFocus = false
                        isPhoneNumberInputFocus = false
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.fillMaxWidth(0.4f)) {
                        CustomLoginInputTextField(
                            modifier = Modifier
                                .padding(start = 25.dp)
                                .focusRequester(focusRequester)
                                .clickable {
                                    focusRequester.requestFocus()
                                },
                            inputEvent = {
                                emailHead = it
                                scope.launch {
                                    validationCheck =
                                        userName.isNotEmpty() && emailHead.isNotEmpty() && emailTail.isNotEmpty() &&
                                                passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty()
                                }
                            },
                            focusState = isEmailInputFocus,
                            label = "이메일",
                            isFocus = {
                                isNameInputFocus = false
                                isEmailInputFocus = true
                                isPasswordFirstInputFocus = false
                                isPasswordSecondInputFocus = false
                                isPhoneNumberInputFocus = false
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    Text(text = "@")
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.42f)
                            .height(30.dp)
                            .padding(bottom = 2.dp),
                        shape = RoundedCornerShape(5),
                        border = BorderStroke(1.dp, PlaceholderColor)
                    ) {
                        CustomLoginInputTextField(
                            modifier = Modifier
                                .padding(top = 3.2.dp, start = 2.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .clickable {
                                    focusRequester.requestFocus()
                                },
                            inputEvent = {
                                emailTail = it
                                scope.launch {
                                    validationCheck =
                                        userName.isNotEmpty() && emailHead.isNotEmpty() && emailTail.isNotEmpty() &&
                                                passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty()
                                }
                            },
                            focusState = false,
                            color = BorderColor,
                            label = "",
                            isFocus = {},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PlaceholderColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "메일인증")
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                CustomLoginInputTextField(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .focusRequester(focusRequester)
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    inputEvent = {
                        passwordFirst = it
                        scope.launch {
                            validationCheck =
                                userName.isNotEmpty() && emailHead.isNotEmpty() && emailTail.isNotEmpty() &&
                                        passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty()
                        }
                    },
                    focusState = isPasswordFirstInputFocus,
                    label = "비밀번호",
                    isFocus = {
                        isNameInputFocus = false
                        isEmailInputFocus = false
                        isPasswordFirstInputFocus = true
                        isPasswordSecondInputFocus = false
                        isPhoneNumberInputFocus = false
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(25.dp))
                CustomLoginInputTextField(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .focusRequester(focusRequester)
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    inputEvent = {
                        passwordSecond = it
                        scope.launch {
                            validationCheck =
                                userName.isNotEmpty() && emailHead.isNotEmpty() && emailTail.isNotEmpty() &&
                                        passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty()
                        }
                    },
                    focusState = isPasswordSecondInputFocus,
                    label = "비밀번호 확인",
                    isFocus = {
                        isNameInputFocus = false
                        isEmailInputFocus = false
                        isPasswordFirstInputFocus = false
                        isPasswordSecondInputFocus = true
                        isPhoneNumberInputFocus = false
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(25.dp))
                CustomLoginInputTextField(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .focusRequester(focusRequester)
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    inputEvent = { phoneNumber = it },
                    focusState = isPhoneNumberInputFocus,
                    label = "전화번호(숫자만 적으세요)",
                    isFocus = {
                        isNameInputFocus = false
                        isEmailInputFocus = false
                        isPasswordFirstInputFocus = false
                        isPasswordSecondInputFocus = false
                        isPhoneNumberInputFocus = true
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(
                Modifier
                    .padding(start = 25.dp, end = 25.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "추가정보", color = BorderColor, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "학교 (기관)",
                        modifier = Modifier.padding(start = 5.dp),
                        color = PlaceholderColor,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.5.dp)
                            .height(30.dp)
                            .clickable {
                                isAreaForBottomSheet = false
                                scope.launch {
                                    bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                        shape = RoundedCornerShape(5),
                        border = BorderStroke(1.dp, PlaceholderColor),
                        color = if (isOrganizationNull) BottomUnSelectedColor else Color.White
                    ) {
                        Text(
                            dept,
                            modifier = Modifier.padding(top = 3.dp, start = 5.dp),
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(13.dp)
                                .clickable {
                                    isOrganizationNull = !isOrganizationNull
                                    deptId = ""
                                    dept = ""
                                },
                            border = BorderStroke(1.dp, PlaceholderColor),
                            shape = CircleShape,
                            color = if (!isOrganizationNull) Color.White else LoginButtonColor
                        ) { }
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "없음", modifier = Modifier.padding(3.dp), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Column {
                    Text(
                        text = "우리 동네 설정",
                        modifier = Modifier.padding(start = 5.dp),
                        color = PlaceholderColor,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.5.dp)
                            .height(30.dp)
                            .clickable {
                                isAreaForBottomSheet = true
                                scope.launch {
                                    bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                        shape = RoundedCornerShape(5),
                        border = BorderStroke(1.dp, PlaceholderColor)
                    ) { Text(address, modifier = Modifier.padding(top = 3.dp, start = 5.dp)) }

                }
            }

            Button(
                onClick = {
                    if (deptId.isEmpty()) deptId = "세코미 베타테스터"
                    if (addressId.isEmpty()) addressId = "사랑시 서대문구 행복동"

                    if (passwordFirst != passwordSecond)
                        showShortToastMessage(context, "비밀번호가 일치하지 않습니다.")
                    else if (userName.isEmpty() || emailHead.isEmpty() || emailTail.isEmpty() || passwordFirst.isEmpty() || passwordSecond.isEmpty())
                        showShortToastMessage(context, "필수 입력정보가 부족합니다.")
                    else if (userName.length > 7)
                        showShortToastMessage(context, "성함은 7자 이내로 작성해주세요.")
                    else if (loginRegisterViewModel.socialType != "")
                        scope.launch {
                            loginRegisterViewModel.postSocialRegister(
                                userName = userName,
                                email = loginRegisterViewModel.socialEmail,
                                userPwd = passwordSecond,
                                userDept = dept,
                                userAddress = address,
                            ).let {
                                when {
                                    it.data?.code == 200 -> {
                                        showLongToastMessage(context, "${it.data?.message}")
                                        loginedUserIdUtil = loginRegisterViewModel.socialEmail
                                        currentUUIDUtil = appSettings.value.uuid

                                        loginRegisterViewModel.getLogin(
                                            loginedUserIdUtil,
                                            passwordSecond,
                                            appSettings.value.uuid
                                        ).let { loginResult ->
                                            accessTokenUtil = loginResult.data!!.data.accessToken
                                        }

                                        onRegisterButtonClick(false)
                                    }
                                    it.data?.code != 200 -> {
                                        Log.d("SocialRegister", "RegisterPage: ${it.data?.code}")
                                        Log.d(
                                            "SocialRegister",
                                            "RegisterPage: ${loginRegisterViewModel.socialSSOID} ${loginRegisterViewModel.socialType}"
                                        )
                                        showLongToastMessage(context, "${it.data?.message}")
                                    }
                                }
                            }
                        }
                    else scope.launch {
                        loginRegisterViewModel.postRegister(
                            userName = userName,
                            email = "$emailHead@$emailTail",
                            userPwd = passwordSecond,
                            userDept = dept,
                            userAddress = address,
                            phone = phoneNumber
                        ).let {
                            when {
                                it.data?.code == 200 -> {
                                    showLongToastMessage(context, "${it.data?.message}")
                                    loginedUserIdUtil = "$emailHead@$emailTail"
                                    currentUUIDUtil = appSettings.value.uuid

                                    loginRegisterViewModel.getLogin(
                                        loginedUserIdUtil, passwordSecond, appSettings.value.uuid
                                    ).let { loginResult ->
                                        accessTokenUtil = loginResult.data!!.data.accessToken
                                    }

                                    onRegisterButtonClick(false)
                                }
                                it.data?.code != 200 -> {
                                    showLongToastMessage(context, "${it.data?.message}")
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                enabled = validationCheck,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonColor,
                    contentColor = Color.White,
                    disabledBackgroundColor = PlaceholderColor
                )
            ) {
                Text(text = "회원가입")
            }
        }
    }
}

@Composable
private fun TermsOfServicePage(
    isFirstTermAgree: Boolean,
    isFirstTermEvent: (Boolean) -> Unit,
    isSecondTermAgree: Boolean,
    isSecondTermEvent: (Boolean) -> Unit,
    isThirdTermAgree: Boolean,
    isThirdTermEvent: (Boolean) -> Unit,
    isTermsAgree: Boolean,
    termsCheck: (Boolean) -> Unit,
    buttonEvent: (Boolean) -> Unit
) {
    var isFirstTermAgreeLocal = isFirstTermAgree
    var isSecondTermAgreeLocal = isSecondTermAgree
    var isThirdTermAgreeLocal = isThirdTermAgree
    var isTermsAgreeLocal = isTermsAgree

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "약관동의",
            fontSize = 20.sp,
            color = PlaceholderColor,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        TermsTextField(termsFirst, isFirstTermAgreeLocal, "동의합니다.") {
            isFirstTermAgreeLocal = it
            isFirstTermEvent(isFirstTermAgreeLocal)
        }
        Spacer(modifier = Modifier.height(10.dp))
        TermsTextField(termsSecond, isSecondTermAgreeLocal, "동의합니다.") {
            isSecondTermAgreeLocal = it
            isSecondTermEvent(isSecondTermAgreeLocal)
        }
        Spacer(modifier = Modifier.height(10.dp))
        TermsTextField(termsThird, isThirdTermAgreeLocal, "동의합니다.") {
            isThirdTermAgreeLocal = it
            isThirdTermEvent(isThirdTermAgreeLocal)
        }
        Divider(Modifier.padding(start = 5.dp, end = 5.dp, top = 20.dp, bottom = 20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            CustomCheckBox(isTermsAgreeLocal, "전체 동의") {
                isTermsAgreeLocal = it
                isFirstTermEvent(it)
                isSecondTermEvent(it)
                isThirdTermEvent(it)
                termsCheck(isTermsAgreeLocal)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                buttonEvent(!isTermsAgreeLocal)
            },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            enabled = isTermsAgreeLocal,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonColor,
                contentColor = Color.White,
                disabledBackgroundColor = PlaceholderColor
            )
        ) {
            Text(text = "다음")
        }
    }
}

@Composable
private fun TermsTextField(
    termText: String,
    termAgreeState: Boolean,
    checkString: String,
    checkEvent: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = termText,
            onValueChange = {},
            modifier = Modifier
                .height(110.dp),
            textStyle = TextStyle(
                fontSize = 11.sp
            ),
            readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = BorderColor,
                backgroundColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        CustomCheckBox(isTermAgree = termAgreeState, checkString) { checkEvent(!termAgreeState) }
    }
}


@Composable
private fun CustomCheckBox(
    isTermAgree: Boolean,
    checkString: String,
    shape: Shape = RoundedCornerShape(3.dp),
    checkEvent: (Boolean) -> Unit
) {
    var borderColor by remember {
        mutableStateOf(BorderColor)
    }
    var checkColor by remember {
        mutableStateOf(Color.White)
    }
    if (isTermAgree) {
        checkColor = LoginButtonColor
        borderColor = LoginButtonColor
    } else {
        checkColor = Color.White
        borderColor = BorderColor
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(13.dp)
                .clickable {
                    checkEvent(!isTermAgree)
                },
            border = BorderStroke(1.dp, borderColor),
            shape = shape,
            color = checkColor
        ) {}
        Text(text = checkString, modifier = Modifier.padding(3.dp), fontSize = 12.sp)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScaffold(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel(),
    socialSignUp: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }

    val userId = remember {
        mutableStateOf(if (isSaveIdUtil) lastLoginedUserIdUtil else "")
    }
    var isUserIdFocus by remember {
        mutableStateOf(false)
    }
    val userPassword = remember {
        mutableStateOf("")
    }
    var isPasswordFocus by remember {
        mutableStateOf(false)
    }
    var message by remember {
        mutableStateOf("")
    }
    var isSaveId by remember {
        mutableStateOf(isSaveIdUtil)
    }
    var isAutoLogin by remember {
        mutableStateOf(isAutoLoginUtil)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            InputLoginInformation(
                userId,
                isUserIdFocus,
                isPasswordFocus,
                userPassword,
            ) { idFocus, passwordFocus ->
                isUserIdFocus = idFocus
                isPasswordFocus = passwordFocus
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            SaveId(isSaveId) {
                isSaveId = it
            }
            AutoLogin(isAutoLogin) {
                isAutoLogin = it
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoginButton {
                scope.launch {
                    loginRegisterViewModel.getLogin(
                        userId.value,
                        userPassword.value,
                        currentUUIDUtil
                    ).let {
                        if (it.data?.code == 200) {
                            accessTokenUtil = it.data!!.data.accessToken
                            loginedUserIdUtil = userId.value
                            setRefreshToken(it.data!!.data.refreshToken)

                            if (isThisFirstTime) {
                                setIsThisFirstTime()
                                loginRegisterViewModel.postAppInit(
                                    uuid = currentUUIDUtil,
                                    refreshToken = refreshTokenUtil
                                )
                            }

                            if (isSaveId) {
                                scope.launch {
                                    setIsSaveId(isSaveId, loginedUserIdUtil)
                                }
                            }

                            if (isAutoLogin)
                                scope.launch {
                                    setIsAutoLogin(true)
                                }
                            else if (!isAutoLogin)
                                scope.launch {
                                    setIsAutoLogin(false)
                                }

                            loginedUserIdUtil = userId.value
                            navController.navigate(SecomiScreens.HomeScreen.name) {
                                popUpTo(SecomiScreens.LoginScreen.name) { inclusive = true }
                            }
                        }

                        message = if (it.data != null) it.data!!.message else "잘못된 접근방법 입니다."
                        showShortToastMessage(context, message)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            FindIdButton(navController = navController)
            Spacer(modifier = Modifier.height(50.dp))
            SocialLoginList(navController, socialSignUp)
        }
    }
}

@Composable
private fun InputLoginInformation(
    userId: MutableState<String>,
    isUserIdFocus: Boolean,
    isPasswordFocus: Boolean,
    userPassword: MutableState<String>,
    onClickIdPasswordFocus: (Boolean, Boolean) -> Unit
) {
    CustomLoginInputTextField(
        modifier = Modifier.padding(start = 25.dp, end = 25.dp),
        inputEvent = {
            userId.value = it
        },
        focusState = isUserIdFocus,
        label = "이메일",
        isFocus = {
            onClickIdPasswordFocus(true, false)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        defaultText = userId.value
    )
    Spacer(modifier = Modifier.height(30.dp))
    CustomLoginInputTextField(
        modifier = Modifier.padding(start = 25.dp, end = 25.dp),
        inputEvent = {
            userPassword.value = it
        },
        focusState = isPasswordFocus,
        label = "비밀번호",
        isFocus = {
            onClickIdPasswordFocus(false, true)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun SaveId(
    isSaveId: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(
        Modifier.padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Checkbox(
            checked = isSaveId,
            onCheckedChange = { onClick(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = LoginButtonColor,
                uncheckedColor = LoginLabelColor
            )
        )
        Text(
            "아이디 저장",
            modifier = Modifier.padding(bottom = 1.dp, start = 42.dp),
            color = LoginGreyTextColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
    }
}


@Composable
fun AutoLogin(
    isAutoLogin: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(
        Modifier.padding(start = 10.dp, top = 35.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Checkbox(
            checked = isAutoLogin,
            onCheckedChange = { onClick(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = LoginButtonColor,
                uncheckedColor = LoginLabelColor
            )
        )
        Text(
            "자동 로그인",
            modifier = Modifier.padding(bottom = 1.dp, start = 42.dp),
            color = LoginGreyTextColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
    }

}

@Composable
fun LoginButton(
    onClickEvent: () -> Unit
) {
    Column {
        Button(
            modifier = Modifier.width(350.dp),
            onClick = {
                onClickEvent()
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor
            )
        ) {
            Text("로그인", color = Color.White)
        }
    }
}

@Composable
fun FindIdButton(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { navController.navigate(SecomiScreens.FindingAccountScreen.name + "/0") }) {
            Text("아이디 찾기", color = Color.DarkGray)
        }
        Text(text = " | ")
        TextButton(onClick = { navController.navigate(SecomiScreens.FindingAccountScreen.name + "/1") }) {
            Text("비밀번호 찾기", color = Color.DarkGray)
        }
    }
}

@Composable
fun SocialLoginList(
    navController: NavController,
    socialSignUp: () -> Unit,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            SocialLoginCard(R.drawable.ic_facebook, "Facebook 로그인") {

            }
            SocialLoginCard(R.drawable.ic_kakaotalk, "Kakao Talk 로그인") {

                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) Log.e("Kakao", "SocialLoginList: 카카오 로그인 실패", error)
                    else if (token != null) {
                        Log.i("Kakao", "SocialLoginList: 카카오 로그인 성공 ${token.accessToken}")

                        UserApiClient.instance.me { user, error1 ->
                            if (error1 != null)
                                showShortToastMessage(context, "잠시 후 다시 시도해주세요.")
                            if (user != null) {
                                Log.d("Kakao", "SocialLoginList: ${user.id}")

                                scope.launch {
                                    loginRegisterViewModel.getSocialLogin(
                                        user.kakaoAccount!!.email!!,
                                        user.id.toString(),
                                        "kakao",
                                        currentUUIDUtil
                                    ).let {
                                        Log.d(
                                            "Social",
                                            "SocialLoginList: email = ${user.kakaoAccount!!.email!!}, id = ${user.id.toString()}"
                                        )
                                        if (it.data?.code == 400) {
                                            loginRegisterViewModel.socialEmail =
                                                user.kakaoAccount!!.email!!
                                            loginRegisterViewModel.socialType = "kakao"
                                            loginRegisterViewModel.socialSSOID = user.id.toString()
                                            socialSignUp()
                                        } else if (it.data?.code == 200) {
                                            accessTokenUtil = it.data!!.data.accessToken
                                            refreshTokenUtil = it.data!!.data.refreshToken
                                            loginedUserIdUtil = user.kakaoAccount!!.email!!
                                            navController.navigate(SecomiScreens.HomeScreen.name) {
                                                popUpTo(SecomiScreens.LoginScreen.name) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }
    SocialLoginCard(R.drawable.ic_naver, "Naver 로그인") {
        val callBack = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.d("NaverLogin", "onError: $errorCode")
                showShortToastMessage(context, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("NaverLogin", "onError: $httpStatus")
                showShortToastMessage(context, "$httpStatus, $message")
            }

            override fun onSuccess() {

            }

        }

        NaverIdLoginSDK.authenticate(context, callBack)
    }
    SocialLoginCard(R.drawable.ic_google, "Google 로그인") {}
}

@Composable
fun SocialLoginCard(
    image: Int,
    buttonText: String,
    loginClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(45.dp)
            .clickable(
                enabled = buttonText == "Kakao Talk 로그인" || buttonText == "Naver 로그인"
            ) {
                loginClick()
            },
        elevation = 4.dp,
        backgroundColor = if (buttonText == "Kakao Talk 로그인" || buttonText == "Naver 로그인") Color.White else PlaceholderColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(buttonText, fontSize = 13.sp, fontWeight = FontWeight.Normal)
        }
    }
}