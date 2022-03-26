package com.sdm.ecomileage.screens.loginRegister

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.MainActivity
import com.sdm.ecomileage.R
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.components.showLongToastMessage
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.*
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun LoginScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel()
) {
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White
        )
    }

    var tabIndex by remember { mutableStateOf(0) }
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
                loginRegisterViewModel,
                navController
            )
            1 -> RegisterScaffold(
                loginRegisterViewModel,
                navController
            )
        }
    }
}

@Composable
fun RegisterScaffold(loginRegisterViewModel: LoginRegisterViewModel, navController: NavController) {

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
        RegisterPage(
            loginRegisterViewModel,
            navController,
            isRegisterDataInputStep,
        ) {
            isRegisterDataInputStep = it
        }
    } else if (!isRegisterDataInputStep) {
        ProfileSubmitPage(navController)
    }
}

@Composable
private fun ProfileSubmitPage(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_default_profile),
                contentDescription = "프로필 변경 아이콘",
                modifier = Modifier.size(170.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "당신의 프로필 사진을\n등록해보세요",
                fontSize = 19.sp,
                color = ProfileDescriptionColor,
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 104.dp, end = 104.dp)
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
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "카메라",
                    modifier = Modifier.padding(start = 1.dp),
                    fontSize = 20.sp,
                    color = ProfileIconColor
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "카메라를 통해 프로필 사진 업로드",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "카메라",
                    modifier = Modifier.padding(start = 1.dp),
                    fontSize = 20.sp,
                    color = ProfileIconColor
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LoginButtonColor,
                    disabledBackgroundColor = PlaceholderColor
                ),
                enabled = false
            ) {
                Text(text = "완료", color = Color.White)
            }
            Button(
                onClick = {
                    navController.navigate(SecomiScreens.HomeScreen.name) {
                        popUpTo(SecomiScreens.LoginScreen.name) { inclusive = true }
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

@Composable
private fun RegisterPage(
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    isRegisterDataInputStep: Boolean,
    onClick: (Boolean) -> Unit
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
    var address by remember {
        mutableStateOf("")
    }
    var isAddressFocus by remember {
        mutableStateOf(false)
    }
    var dept by remember {
        mutableStateOf("")
    }
    var isDeptFocus by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize(),
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
            InputTextField(
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
                    isAddressFocus = false
                    isDeptFocus = false
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
                    InputTextField(
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
                            isAddressFocus = false
                            isDeptFocus = false
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                Text(text = "@")
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.42f)
                        .height(30.dp),
                    shape = RoundedCornerShape(5),
                    border = BorderStroke(1.dp, PlaceholderColor)
                ) {
                    InputTextField(
                        modifier = Modifier
                            .padding(top = 2.dp)
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
            InputTextField(
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
                    isAddressFocus = false
                    isDeptFocus = false
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(25.dp))
            InputTextField(
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
                    isAddressFocus = false
                    isDeptFocus = false
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(25.dp))
            InputTextField(
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
                    isAddressFocus = false
                    isDeptFocus = false
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
                        .height(30.dp),
                    shape = RoundedCornerShape(5),
                    border = BorderStroke(1.dp, PlaceholderColor)
                ) {
                    InputTextField(
                        modifier = Modifier
                            .padding(top = 3.dp, start = 5.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .clickable {
                                focusRequester.requestFocus()
                            },
                        inputEvent = { dept = it },
                        focusState = isDeptFocus,
                        color = BorderColor,
                        label = "",
                        isFocus = {
                            isNameInputFocus = false
                            isEmailInputFocus = false
                            isPasswordFirstInputFocus = false
                            isPasswordSecondInputFocus = false
                            isPhoneNumberInputFocus = false
                            isDeptFocus = true
                            isAddressFocus = false
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                var backgroundColor by remember {
                    mutableStateOf(Color.White)
                }
                var isOrganizationNull by remember {
                    mutableStateOf(false)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(13.dp)
                            .clickable {
                                isOrganizationNull = !isOrganizationNull
                                backgroundColor =
                                    if (isOrganizationNull) Color.White else LoginButtonColor
                            },
                        border = BorderStroke(1.dp, PlaceholderColor),
                        shape = CircleShape,
                        color = backgroundColor
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
                        .height(30.dp),
                    shape = RoundedCornerShape(5),
                    border = BorderStroke(1.dp, PlaceholderColor)
                ) {
                    InputTextField(
                        modifier = Modifier
                            .padding(top = 3.dp, start = 5.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .clickable {
                                focusRequester.requestFocus()
                            },
                        inputEvent = { address = it },
                        focusState = isAddressFocus,
                        color = BorderColor,
                        label = "",
                        isFocus = {
                            isNameInputFocus = false
                            isEmailInputFocus = false
                            isPasswordFirstInputFocus = false
                            isPasswordSecondInputFocus = false
                            isPhoneNumberInputFocus = false
                            isDeptFocus = false
                            isAddressFocus = true
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }

            }

            Spacer(Modifier.height(30.dp))



            Button(
                onClick = {
                    if (dept.isEmpty()) dept = "세코미 베타테스터"
                    if (address.isEmpty()) address = "사랑시 서대문구 행복동"

                    if (passwordFirst != passwordSecond)
                        showShortToastMessage(context, "비밀번호가 일치하지 않습니다.")
                    else if (userName.isEmpty() || emailHead.isEmpty() || emailTail.isEmpty() || passwordFirst.isEmpty() || passwordSecond.isEmpty())
                        showShortToastMessage(context, "필수 입력정보가 부족합니다.")
                    else if (userName.length > 14)
                        showShortToastMessage(context, "성함은 14자 이내로 작성해주세요.")
                    else scope.launch {
                        loginRegisterViewModel.postRegister(
                            userName = userName,
                            email = "$emailHead@$emailTail",
                            userPwd = passwordSecond,
                            userDept = dept,
                            userAddress = address,
                        ).let {
                            when {
                                it.data?.code == 200 -> {
                                    showLongToastMessage(context, "${it.data?.message}")
                                    navController.navigate(SecomiScreens.LoginScreen.name) {
                                        launchSingleTop
                                    }
                                }
                                it.data?.code != 200 -> {
                                    showLongToastMessage(context, "${it.data?.message}")
                                }
                            }
                        }
                    }

//                    onClick(!test)

                },
                modifier = Modifier
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
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    val userId = remember {
        mutableStateOf(loginRegisterViewModel.dataStore.value?.lastId ?: "")
    }
    var isUserIdFocus by remember {
        mutableStateOf(false)
    }
    val userPassword = remember {
        mutableStateOf(loginRegisterViewModel.dataStore.value?.lastPassword ?: "")
    }
    var isPasswordFocus by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var message by remember {
        mutableStateOf("")
    }
    var isSaveId by remember {
        mutableStateOf(false)
    }
    var isAutoLogin by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            InputLoginInformation(userId, isUserIdFocus, isPasswordFocus, userPassword){ idFocus, passwordFocus ->
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
                    ).let {
                        if (it.data?.code == 200) {
                            accessToken = it.data!!.data.accessToken
                            loginedUserId = userId.value

                            if (isSaveId)
                                loginRegisterViewModel.updateId(loginId = userId.value)
                            if (isAutoLogin)
                                loginRegisterViewModel.updateAutoLogin(
                                    loginId = userId.value,
                                    loginPassword = userPassword.value
                                )

                            if (loginRegisterViewModel.dataStore.value?.uuid == null){
                                loginRegisterViewModel.updateUUID(uuid = UUID.randomUUID().toString())
                                Log.d("LoginScreen", "LoginScaffold: UUID = ${loginRegisterViewModel.dataStore.value?.uuid}")
                            }

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
            SocialLoginList(navController, loginRegisterViewModel)
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

    InputTextField(
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
        )
    )
    Spacer(modifier = Modifier.height(30.dp))
    InputTextField(
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputTextField(
    modifier: Modifier,
    inputEvent: (String) -> Unit,
    focusState: Boolean,
    color: Color = LoginLabelColor,
    label: String,
    isFocus: () -> Unit,
    keyboardOptions: KeyboardOptions
) {
    var text by remember {
        mutableStateOf("")
    }
    var labelPositionX by remember {
        mutableStateOf(0.dp)
    }
    var labelPositionY by remember {
        mutableStateOf(0.dp)
    }

    if (text.isNotEmpty()) {
        labelPositionX = (-2).dp
        labelPositionY = (-20).dp
    } else {
        labelPositionX = 0.dp
        labelPositionY = 0.dp
    }

    val xOffsetAnimation: Dp by animateDpAsState(
        if (!focusState) labelPositionX else (-2).dp
    )
    val yOffsetAnimation: Dp by animateDpAsState(
        if (!focusState) labelPositionY else (-20).dp
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            inputEvent(it)
            isFocus()
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        textStyle = TextStyle(
            LoginEmailInputColor
        ),
        singleLine = true,
        visualTransformation = if (label == "비밀번호" || label == "비밀번호 확인") PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            inputEvent(text)
            keyboardController?.hide()
        })
    ) { innerTextField ->
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .absoluteOffset(x = xOffsetAnimation, y = yOffsetAnimation),
                style = MaterialTheme.typography.caption,
                color = color
            )
            Column {
                innerTextField()
                Divider(
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth(),
                    color = if (focusState) LoginButtonColor else PlaceholderColor
                )
            }
        }
    }
}

@Composable
fun SaveId(
    isSaveId: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.padding(bottom = 1.dp),
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
    Row(
        Modifier.padding(start = 10.dp, top = 35.dp),
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.padding(bottom = 1.dp),
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
        TextButton(onClick = { navController.navigate(SecomiScreens.FindingAccountScreen.name) }) {
            Text("아이디", color = Color.DarkGray)
        }
        Text(text = " | ")
        TextButton(onClick = { navController.navigate(SecomiScreens.FindingAccountScreen.name) }) {
            Text("비밀번호 찾기", color = Color.DarkGray)
        }
    }
}

@Composable
fun SocialLoginList(navController: NavController, loginRegisterViewModel: LoginRegisterViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            SocialLoginCard(R.drawable.ic_facebook, "Facebook 로그인") {}
            SocialLoginCard(R.drawable.ic_kakaotalk, "Kakao Talk 로그인") {
//
//                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
//                    if (error != null) Log.e("Login", "SocialLoginList: 카카오 로그인 실패", error)
//                    else if (token != null) {
//                        Log.i("Login", "SocialLoginList: 카카오 로그인 성공 ${token.accessToken}")
//                        navController.navigate(SecomiScreens.LoginScreen.name)
//                    }
//                }
//
//                UserApiClient.instance.me { user, error ->
//                    if (error != null)
//                        showToastMessage(context, "잠시 후 다시 시도해주세요.")
//                    if (user != null)
//                        scope.launch {
//                            loginRegisterViewModel.postRegister(
//                                userId = user.kakaoAccount.toString(),
//                                userName = user.id.toString(),
//                                userPwd = "1q2w3e4r1a",
//                                email = user.kakaoAccount.toString(),
//                                userDept = "기후환경 마일리지 앱 베타 테스터",
//                                userAddress = "사랑시 서대문구 행복동"
//                            ).let {
//
//                                if (it.data?.code == 200) {
//                                    loginRegisterViewModel.getLogin(
//                                        user.kakaoAccount.toString(),
//                                        "1q2w3e4r1a"
//                                    )
//
//                                    navController.navigate(SecomiScreens.HomeScreen.name) {
//                                        popUpTo(SecomiScreens.LoginScreen.name) {
//                                            inclusive = true
//                                        }
//                                    }
//                                } else if (it.data?.code == 401) {
//                                    loginRegisterViewModel.getLogin(
//                                        user.kakaoAccount.toString(),
//                                        "1q2w3e4r1a"
//                                    )
//
//                                    navController.navigate(SecomiScreens.HomeScreen.name) {
//                                        popUpTo(SecomiScreens.LoginScreen.name) {
//                                            inclusive = true
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                }
            }
            SocialLoginCard(R.drawable.ic_naver, "Naver 로그인") {}
            SocialLoginCard(R.drawable.ic_google, "Google 로그인") {}
        }
    }
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
            .clickable {
                loginClick()
            },
        elevation = 4.dp
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


@Composable
fun BasicInformText() {
    Row {
        Text(text = "＊", color = Color.Red)
        Text(text = "기본정보", color = Color.DarkGray)
    }
}