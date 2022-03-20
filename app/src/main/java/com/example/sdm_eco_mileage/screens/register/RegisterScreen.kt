package com.example.sdm_eco_mileage.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sdm_eco_mileage.screens.findingAccount.PhoneNumberTextField
import com.example.sdm_eco_mileage.screens.login.PasswordTextField
import com.example.sdm_eco_mileage.ui.theme.LoginLabelColor
import com.example.sdm_eco_mileage.ui.theme.LoginButtonColor
import com.example.sdm_eco_mileage.ui.theme.SendingEmailMessageColor


@Preview
@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicInformText()
            Column(modifier = Modifier.padding(top = 30.dp)) {
                NameTextField()
                EmailTextField()
                SendEmailCodeMessage()
                PhoneNumberTextField()
                PasswordTextField(){}
                PasswordCheckTextField()
            }
            Column(
                modifier = Modifier.padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MoreInformText()
            }
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            SchoolInformText()
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            SchoolSearchTextField()
        }
        Column() {
            NoSchoolInformCheck()
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            AddressInformText()
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            AddressSearchTextField()
        }
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterButton()
        }
    }

}

@Composable
fun BasicInformText() {
    Row() {
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
        onValueChange = { text = it },
        label = { Text("성함") },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        ),
        modifier = Modifier.width(350.dp)
    )
}

@Composable
fun EmailTextField() {
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        Row() {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("이메일") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                )
            )
            Column(modifier = Modifier.padding(top = 10.dp, start = 5.dp)) {
                Surface(
                    modifier = Modifier
                        .width(70.dp)
                        .height(40.dp),
                    shape = RectangleShape,
                    color = LoginLabelColor
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("메일인증", color = Color.White)
                    }
                }
            }
        }
    }
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
        ),
        modifier = Modifier.width(350.dp)
    )
}


@Composable
fun MoreInformText() {
    Row() {
        Text(text = "추가정보", color = Color.DarkGray)
    }
}

@Composable
fun RegisterButton() {
    Column() {
        Button(
            modifier = Modifier.width(350.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor
            )
        ) {
            Text("회원가입", color = Color.White)
        }
    }
}

@Composable
fun SchoolInformText() {
    Row() {
        Text(text = "학교(기관)", color = Color.DarkGray)
    }
}

@Composable
fun NoSchoolInformCheck(){
    Row() {
        val checkboxState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it}
        )
        Column(modifier = Modifier.padding(top = 14.dp)) {
            Text("없음", color = Color.DarkGray)
        }
    }
}

@Composable
fun AddressInformText() {
    Row() {
        Text(text = "우리동네 설정", color = Color.DarkGray)
    }
}

@Composable
fun SchoolSearchTextField() {
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        Row() {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                ),
                modifier = Modifier.height(40.dp)
            )
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Surface(
                    modifier = Modifier
                        .width(70.dp)
                        .height(40.dp),
                    shape = RectangleShape,
                    color = LoginLabelColor
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("검색", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AddressSearchTextField() {
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        Row() {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                ),
                modifier = Modifier.height(40.dp)
            )
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Surface(
                    modifier = Modifier
                        .width(70.dp)
                        .height(40.dp),
                    shape = RectangleShape,
                    color = LoginLabelColor
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("검색", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun SendEmailCodeMessage(){
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "인증메일을 발송하였습니다.", color = SendingEmailMessageColor)
            OutlinedTextField(
                value = text,
                label = { Text("인증코드") },
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp)
            )
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Surface(
                    modifier = Modifier
                        .width(70.dp)
                        .height(40.dp),
                    shape = RectangleShape,
                    color = LoginLabelColor
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("인증하기", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AgreementMessage(){
    Column() {
        Text(text = "약관동의", color = Color.DarkGray)
    }
}

@Composable
fun AgreementContents1(){
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .height(138.dp)
                .width(350.dp)
        )
        Row() {
            val checkboxState = remember {
                mutableStateOf(false)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text("동의합니다.", color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun AgreementContents2(){
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .height(138.dp)
                .width(350.dp)
        )
        Row() {
            val checkboxState = remember {
                mutableStateOf(false)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text("동의합니다.", color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun AgreementContents3(){
    var text by remember {
        mutableStateOf("")
    }
    Column() {
        OutlinedTextField(
            value = text,
            enabled = false,
            onValueChange = {  },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .height(138.dp)
                .width(350.dp)
        )
        Row() {
            val checkboxState = remember {
                mutableStateOf(false)
            }
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = { checkboxState.value = it}
            )
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text("동의합니다.", color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun AllAgreementCheckBox(){
    Row() {
        val checkboxState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it}
        )
        Column(modifier = Modifier.padding(top = 14.dp)) {
            Text("전체동의", color = Color.DarkGray)
        }
    }
}

@Composable
fun AgreementNextButton() {
    Column() {
        Button(
            modifier = Modifier.width(350.dp),
            onClick = { },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = LoginButtonColor)
        ) {
            Text("다음", color = Color.White)
        }
    }
}