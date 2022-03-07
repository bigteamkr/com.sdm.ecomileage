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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R


@Preview
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            LoginTextField()
            PasswordTextField()
        }
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(1f)) {
            SaveId()
            AutoLogin()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            LoginButton()
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
    Column(modifier = Modifier) {
        Row(horizontalArrangement = Arrangement.Center) {
            val checkboxState = remember {
                mutableStateOf(true)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Text("아이디 저장")
        }
    }
}


@Composable
fun AutoLogin(){
    Column() {
        Row() {
            val checkboxState = remember {
                mutableStateOf(true)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Text("자동 로그인")
        }
    }
}

@Composable
fun LoginButton() {
    Column() {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Green)
        ) {
            Text("로그인")
        }
    }
}

@Composable
fun FindIdButton() {
    TextButton(onClick = { /*TODO*/ }) {
        Text("아이디 | 비밀번호 찾기")
    }
}

@Composable
fun SocialLoginCard(){
    Column() {
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp)) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.ic_kakao_icon), contentDescription = null)
                    Text("카카오톡 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp)) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.ic_facebook), contentDescription = null)
                    Text("페이스북 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp)) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = null)
                    Text("구글 로그인")
                }
            }
        }
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
            Card(shape = RoundedCornerShape(8.dp)) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.ic_naver), contentDescription = null)
                    Text("네이버 로그인")
                }
            }
        }
    }
}