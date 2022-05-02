package com.sdm.ecomileage.screens.findingAccount

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdm.ecomileage.components.CustomLoginInputTextField
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FindingAccountScreen(
    navController: NavController,
    type: Int
) {
    var tabIndex by remember { mutableStateOf(type) }
    val tabTitles = listOf("아이디 찾기", "비밀번호 찾기")

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
            0 -> {
                FindingIdScaffold(navController)
            }
            1 -> {
                FindingPasswordScaffold(navController)
            }
        }
    }
}


@Composable
fun FindingIdScaffold(
    navController: NavController
) {

    var name by remember { mutableStateOf("") }
    var getResponse by remember { mutableStateOf(false) }
    var emailId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (getResponse)
            FindingIdResult(name, emailId)
        else
            FindingInputColumn("ID", "이름", "전화번호") { nameResult, emailResult ->
                name = nameResult
                emailId = emailResult
                getResponse = true
            }
        LoginRegisterButtonRow(navController)
    }

}

@Composable
private fun LoginRegisterButtonRow(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "로그인",
            modifier = Modifier.clickable {
                navController.navigate(SecomiScreens.LoginScreen.name + "/0") {
                    launchSingleTop = true
                    popUpTo(SecomiScreens.FindingAccountScreen.name) { inclusive = true }
                }
            },
            color = PlaceholderColor
        )
        Text(text = "  |  ")
        Text(
            text = "회원가입",
            modifier = Modifier.clickable {
                navController.navigate(SecomiScreens.LoginScreen.name + "/1") {
                    launchSingleTop = true
                    popUpTo(SecomiScreens.FindingAccountScreen.name) { inclusive = true }
                }
            },
            color = PlaceholderColor
        )
    }
}

@Composable
private fun FindingInputColumn(
    type: String,
    firstLabel: String,
    secondLabel: String,
    findingAccountViewModel: FindingAccountViewModel = hiltViewModel(),
    onAction: (String, String) -> Unit
) {
    val scope = rememberCoroutineScope()

    var resultIdFail by remember { mutableStateOf(false) }
    var resultPasswordFail by remember { mutableStateOf(false) }
    var resultPasswordSuccess by remember { mutableStateOf(false) }
    var firstParameter by remember { mutableStateOf("") }
    var secondParameter by remember { mutableStateOf("") }
    var currentFocus by remember { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(110.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            when {
                resultIdFail -> Text(
                    text = "조회되는 아이디가 없습니다.",
                    fontSize = 15.sp,
                    color = FindingAccountErrorColor,
                )
                resultPasswordFail -> Text(
                    text = "성함과 이메일이 일치하지 않습니다.",
                    color = FindingAccountErrorColor
                )
                resultPasswordSuccess -> Text(
                    text = "메일에 비밀번호를 성공적으로 발송하였습니다.",
                    color = LoginButtonColor
                )
            }
        }

        CustomLoginInputTextField(
            modifier = Modifier.padding(horizontal = 20.dp),
            inputEvent = { firstParameter = it },
            focusState = currentFocus == firstLabel,
            label = firstLabel,
            isFocus = {
                currentFocus = firstLabel
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Spacer(Modifier.height(30.dp))

        CustomLoginInputTextField(
            modifier = Modifier.padding(horizontal = 20.dp),
            inputEvent = { secondParameter = it },
            focusState = currentFocus == secondLabel,
            label = secondLabel,
            isFocus = {
                currentFocus = secondLabel
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (type == "ID") KeyboardType.NumberPassword else KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(42.dp))

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    if (type == "ID")
                        findingAccountViewModel.getFindingId(
                            userName = firstParameter,
                            userTel = secondParameter
                        ).let {
                            if (it.data?.code == 200) {
                                onAction(firstParameter, it.data!!.userid)
                            } else {
                                resultIdFail = true
                                Log.d("Finding page", "FindingInputColumn: ${it.data?.message}")
                                Log.d(
                                    "Finding page",
                                    "FindingInputColumn: $firstParameter, $secondParameter"
                                )
                            }

                        }
                    else if (type == "PASSWORD")
                        findingAccountViewModel.getFindingPassword(
                            userName = firstParameter,
                            userEmail = secondParameter
                        ).let {
                            if (it.data?.code == 200)
                                resultPasswordSuccess = true
                            else
                                resultPasswordFail = true
                        }
                }
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LoginButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(text = if (type == "ID") "아이디 조회" else "비밀번호 발급메일 발송")
        }
    }
}

@Composable
private fun FindingIdResult(name: String, emailId: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(110.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$name 님이 가입하신 이메일은",
                modifier = Modifier.padding(top = 80.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Surface(
            shape = RoundedCornerShape(10)
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .height(130.dp)
                    .fillMaxWidth()
                    .background(SurfaceGreyColor),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = emailId, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun FindingPasswordScaffold(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FindingInputColumn("PASSWORD", "성함", "이메일") { _, _ -> }
        LoginRegisterButtonRow(navController)
    }
}