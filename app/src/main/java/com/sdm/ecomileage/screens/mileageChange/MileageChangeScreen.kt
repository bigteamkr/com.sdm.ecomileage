package com.sdm.ecomileage.screens.mileageChange

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.CustomLoginInputTextField
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.mileagechange.response.MileageChangeResponse
import com.sdm.ecomileage.model.myPage.myFeedInfo.response.MyFeedInfoResponse
import com.sdm.ecomileage.model.registerPage.register.request.AppRegister
import com.sdm.ecomileage.model.registerPage.register.request.RegisterRequest
import com.sdm.ecomileage.model.registerPage.register.response.RegisterResponse
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.mileageChange.MileageChangeScreen
import com.sdm.ecomileage.screens.mileageChange.MileageChangeModelAndView
import com.sdm.ecomileage.screens.myPage.mileageHistoryItem
import com.sdm.ecomileage.ui.theme.CardContentColor
import com.sdm.ecomileage.ui.theme.LoginButtonColor
import com.sdm.ecomileage.ui.theme.MileageChangeBackgroundColor
import com.sdm.ecomileage.ui.theme.PlaceholderColor
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import kotlinx.coroutines.launch

@Composable
fun MileageChangeScreen(navController: NavController, systemUiController: SystemUiController) {
    LaunchedEffect(key1 = true) {
        systemUiController.setStatusBarColor(MileageChangeBackgroundColor)
    }

    Content(navController)
    // MileageChangeModelAndView = hiltViewModel()
    // mileageChangeModelAndView : MileageChangeModelAndView  = hiltViewModel()


}


@Composable
private fun Content(navController: NavController) {
    var uuid: String;
    var mileagetype: String;
    var gifttype: String;
    var persno: String

    val mileageChangeViewModel : MileageChangeModelAndView = hiltViewModel()

    // current configuration
    val configuration = LocalConfiguration.current

    var currentOption by remember {
        mutableStateOf("")
    }
    var firstID by remember {
        mutableStateOf("")
    }
    var firstIDFocus by remember { mutableStateOf(false) }

    var secondID by remember {
        mutableStateOf("")
    }
    var secondIDFocus by remember { mutableStateOf(false) }

    val context = LocalContext.current



    // val mileageChangeModelAndView = MileageChangeModelAndView().postMileageChange(uuid, mileagetype, gifttype, persno)
    val coroutineScope = rememberCoroutineScope()

       Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                SecomiTopAppBar(
                    title = "마일리지 전환",
                    backgroundColor = listOf(
                        MileageChangeBackgroundColor,
                        MileageChangeBackgroundColor
                    ),
                    navigationIcon = painterResource(id = R.drawable.ic_out),
                    currentScreen = SecomiScreens.MileageChangeScreen.name,
                    navController = navController
                )
            },
            backgroundColor = MileageChangeBackgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    options(
                        R.drawable.image_volunteer_prove,
                        "봉사 인증",
                        currentOption == "봉사 인증"
                    ) { currentOption = it }
                    options(
                        R.drawable.image_education_time_prove,
                        "교육시간 인증",
                        currentOption == "교육시간 인증"
                    ) { currentOption = it }
                    options(
                        R.drawable.image_local_voucher,
                        "지역사랑 상품권 전환",
                        currentOption == "지역사랑 상품권 전환"
                    ) { currentOption = it }


                    Spacer(modifier = Modifier.height(60.dp))

                    Column() {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "마일리지 전환신청자 주민등록번호",
                                fontSize = 17.sp,
                                color = PlaceholderColor,
                                letterSpacing = 1.0.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Row() {
                            Row(modifier = Modifier.width((configuration.screenWidthDp * 0.3).dp)) {
                                CustomLoginInputTextField(
                                    modifier = Modifier,
                                    inputEvent = {
                                        firstID = it
                                    },
                                    focusState = firstIDFocus,
                                    label = "주민번호 앞자리",
                                    isFocus = {
                                        firstIDFocus = true
                                        secondIDFocus = false
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.NumberPassword,
                                        imeAction = ImeAction.Done
                                    ),
                                    defaultText = firstID
                                )
                            }

                            Text(
                                text = "-",
                                modifier = Modifier.padding(horizontal = 15.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                color = PlaceholderColor
                            )

                            Row(modifier = Modifier.width((configuration.screenWidthDp * 0.3).dp)) {
                                CustomLoginInputTextField(
                                    modifier = Modifier,
                                    inputEvent = {
                                        secondID = it
                                    },
                                    focusState = secondIDFocus,
                                    label = "주민번호 뒷자리",
                                    isFocus = {
                                        firstIDFocus = false
                                        secondIDFocus = true
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.NumberPassword,
                                        imeAction = ImeAction.Done
                                    ),
                                    defaultText = secondID
                                )
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    enabled = currentOption != "" && firstID.length == 6 && secondID.length == 7,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LoginButtonColor,
                        contentColor = Color.White,
                        disabledBackgroundColor = PlaceholderColor,
                        disabledContentColor = Color.White
                    ),
                    onClick = {
                        uuid = currentUUIDUtil
                        mileagetype = currentOption
                        gifttype = ""
                        persno = firstID + " " + secondID

                        coroutineScope.launch {
                            // debugging message 마일리지 전환신청 완료 parameter
                            showShortToastMessage(context,
                                uuid + " " + mileagetype + " " + gifttype + " " + persno)

                            mileageChangeViewModel.postMileageChange(uuid,
                                mileagetype,
                                gifttype,
                                persno)
                        }
                        // homescreen으로 이동
                        navController.navigate(SecomiScreens.HomeScreen.name)

                    }
                ) {
                    Text(text = "전환 신청하기")
                }
            }
        }
    }


@Composable
private fun options(
    image: Int,
    text: String,
    isClicked: Boolean,
    onClick: (String) -> Unit
) {
    val configuration = LocalConfiguration.current

    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .clickable { if (isClicked) onClick("") else onClick(text) },
        elevation = 5.dp,
        border = if (isClicked) BorderStroke(1.0.dp, LoginButtonColor) else null
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height((configuration.screenHeightDp * 0.09).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = text,
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 18.sp,
                color = CardContentColor,
                letterSpacing = 1.0.sp,
                fontWeight = if (isClicked) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}


