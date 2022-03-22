package com.sdm.ecomileage.screens.findingAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdm.ecomileage.screens.register.RegisterTextField
import com.sdm.ecomileage.ui.theme.FindingAccountErrorColor
import com.sdm.ecomileage.ui.theme.LoginButtonColor

@Preview
@Composable
fun FindingAccountScreen(/* todo::navController: NavController, systemUiController: SystemUiController*/) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(bottom = 30.dp)) {
            FindingAccountErrorMessage()
        }
        Column {
            RegisterTextField(Modifier, "성함", KeyboardOptions(keyboardType = KeyboardType.Email))
            PhoneNumberTextField()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 30.dp)){
            FindAccountButton()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 15.dp, bottom = 330.dp)){
            LoginRegisterButton()
        }
    }
}

@Composable
fun FindingAccountErrorMessage(){
    Text(text = "조회되는 아이디가 없습니다.", color = FindingAccountErrorColor)
}

@Composable
fun PhoneNumberTextField() {
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("전화번호(숫자만 적으세요)") },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        ),
        modifier = Modifier.width(350.dp)
    )
}

@Composable
fun FindAccountButton() {
    Column() {
        Button(
            modifier = Modifier.width(350.dp),
            onClick = { },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor
            )
        ) {
            Text("아이디 조회", color = Color.White)
        }
    }
}

@Composable
fun LoginRegisterButton() {
    TextButton(onClick = {  }) {
        Text("로그인 | 회원가입", color = Color.DarkGray)
    }
}