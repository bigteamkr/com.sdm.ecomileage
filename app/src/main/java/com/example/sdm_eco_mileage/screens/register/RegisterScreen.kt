package com.example.sdm_eco_mileage.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sdm_eco_mileage.screens.login.PasswordTextField
import com.example.sdm_eco_mileage.ui.theme.LoginButtonColor
import com.google.accompanist.systemuicontroller.SystemUiController


@Preview
@Composable
fun RegisterScreen(navController: NavController, systemUiController: SystemUiController) {
    Column(modifier = Modifier
        .background(Color.White)
        .padding(top = 30.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        BasicInformText()
        Column(modifier = Modifier.padding(top = 30.dp)) {
            NameTextField()
            PasswordTextField()
            PasswordCheckTextField()
        }
        Column(modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            MoreInformText()
        }
        Column(modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            RegisterButton()
        }
    }
}

@Composable
fun BasicInformText(){
    Row {
        Text(text = "＊", color = Color.Red)
        Text(text = "기본정보", color = Color.DarkGray)
    }
}



@Composable
fun NameTextField() {
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = { text = it } ,
        label = { Text("성함") },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        )
    )
}


@Composable
fun PasswordCheckTextField() {
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
fun MoreInformText(){
    Row {
        Text(text = "추가정보", color = Color.DarkGray)
    }
}

@Composable
fun RegisterButton() {
    Column {
        Button(
            modifier = Modifier.width(300.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor
            )
        ) {
            Text("회원가입", color = Color.White)
        }
    }
}