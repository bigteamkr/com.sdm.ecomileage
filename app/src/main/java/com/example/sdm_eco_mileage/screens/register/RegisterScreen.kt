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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.screens.findingAccount.PhoneNumberTextField
import com.example.sdm_eco_mileage.screens.login.PasswordTextField
import com.example.sdm_eco_mileage.ui.theme.CertificationButtonColor
import com.example.sdm_eco_mileage.ui.theme.LoginButtonColor


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
                PhoneNumberTextField()
                PasswordTextField()
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
            Column(modifier = Modifier.padding(top = 16.dp, start = 5.dp)) {
                Surface(
                    modifier = Modifier
                        .width(70.dp)
                        .height(40.dp),
                    shape = RectangleShape,
                    color = CertificationButtonColor
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
                    color = CertificationButtonColor
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
                    color = CertificationButtonColor
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