package com.example.sdm_eco_mileage.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.LoginButtonColor
import com.example.sdm_eco_mileage.ui.theme.LoginGreyTextColor
import com.example.sdm_eco_mileage.ui.theme.LoginLabelColor
import com.example.sdm_eco_mileage.ui.theme.LoginTextFieldFocusedColor
import com.example.sdm_eco_mileage.utils.accessToken
import com.example.sdm_eco_mileage.utils.uuidSample
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val userId = remember {
        mutableStateOf("")
    }
    val userPassword = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            LoginTextField {
                userId.value = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextField {
                userPassword.value = it
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            SaveId()
            AutoLogin()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoginButton {
                scope.launch() {
                    if (loginViewModel.getLogin(
                            userId.value,
                            userPassword.value
                        ).data?.code == 200
                    ) {

                        Log.d("TAG", "LoginScreen: $uuidSample")
                        accessToken = loginViewModel.getLogin(
                            userId.value,
                            userPassword.value
                        ).data!!.data.accessToken

                        navController.navigate(SecomiScreens.HomeScreen.name) {
                            popUpTo(SecomiScreens.LoginScreen.name) { inclusive = true }
                        }
                    }
                }
            }
            FindIdButton(navController = navController)
        }
        Column(modifier = Modifier.padding(top = 50.dp)) {
            SocialLoginCard()
        }
    }
}

@Composable
private fun LoginTextField(
    inputEvent: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    var isFocus by remember {
        mutableStateOf(false)
    }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            inputEvent(it)
        },
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(
            LoginButtonColor
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Default
        ),
        keyboardActions = KeyboardActions(onDone = { inputEvent(text) })
    ) { innerTextField ->
        Column(
            Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocus = focusState.isFocused
                }
                .focusRequester(focusRequester)
        ) {
            Text(text = "이메일", style = MaterialTheme.typography.caption, color = LoginLabelColor)
            innerTextField()
            Divider(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                color = if (isFocus) LoginButtonColor else Color.Black
            )
        }
    }
}

@Composable
fun PasswordTextField(
    inputEvent: (String) -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = {
            password = it
            inputEvent(it)
        },
        label = { Text("비밀번호", fontSize = 13.sp) },
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f),
            focusedIndicatorColor = LoginTextFieldFocusedColor,
            unfocusedIndicatorColor = Color.Black,
            focusedLabelColor = LoginLabelColor,
            unfocusedLabelColor = LoginLabelColor
        ),
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions { inputEvent(password) }
    )
}

@Composable
fun SaveId() {
    val checkboxState = remember {
        mutableStateOf(false)
    }

    Row(
        Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it },
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
fun AutoLogin() {
    val checkboxState = remember {
        mutableStateOf(false)
    }

    Row(
        Modifier.padding(start = 10.dp, top = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it },
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
    Column() {
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
    TextButton(onClick = { navController.navigate(SecomiScreens.FindingAccountScreen.name) }) {
        Text("아이디 | 비밀번호 찾기", color = Color.DarkGray)
    }
}

@Composable
fun SocialLoginCard() {
    Column() {
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kakaotalk),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("KakaoTalk 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("facebook 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Google 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_naver),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Naver 로그인")
                }
            }
        }
    }
}