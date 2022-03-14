package com.example.sdm_eco_mileage.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.LoginButtonColor
import com.example.sdm_eco_mileage.ui.theme.TagColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor
import com.google.accompanist.systemuicontroller.SystemUiController


@Preview
@Composable
fun LoginScreen(navController: NavController, systemUiController: SystemUiController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            LoginTextField()
            PasswordTextField()
        }
        Column(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(1f)) {
            SaveId()
            AutoLogin()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            LoginButton(navController = navController)
            FindIdButton()
        }
        Column(modifier = Modifier.padding(top = 50.dp)){
            SocialLoginCard()
        }
    }
}

@Composable
private fun LoginTextField() {
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = { text = it } ,
        label = { Text("이메일")},
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        )
    )
}

@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("비밀번호") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        )
    )
}

@Composable
fun SaveId() {
    Column(modifier = Modifier.padding(start = 20.dp)) {
        Row {
            val checkboxState = remember {
                mutableStateOf(true)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text("아이디 저장", color = Color.DarkGray)
            }
        }
    }
}


@Composable
fun AutoLogin(){
    Column(modifier = Modifier.padding(start = 20.dp)) {
        Row {
            val checkboxState = remember {
                mutableStateOf(true)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text("자동 로그인", color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun LoginButton(navController: NavController) {
    Column {
        Button(
            modifier = Modifier.width(300.dp),
            onClick = { navController.navigate(SecomiScreens.HomeScreen.name) },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor)
        ) {
            Text("로그인", color = Color.White)
        }
    }
}

@Composable
fun FindIdButton() {
    TextButton(onClick = { /*TODO*/ }) {
        Text("아이디 | 비밀번호 찾기", color = Color.DarkGray)
    }
}

@Composable
fun SocialLoginCard(){
    Column {
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(painter = painterResource(id = R.drawable.ic_kakaotalk), contentDescription = null)
                    }
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("KakaoTalk 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(painter = painterResource(id = R.drawable.ic_facebook), contentDescription = null)
                    }
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("facebook 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = null)
                    }
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Google 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Image(painter = painterResource(id = R.drawable.ic_naver), contentDescription = null)
                    }
                }
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Naver 로그인")
                }
            }
        }
    }
}