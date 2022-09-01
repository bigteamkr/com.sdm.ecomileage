package com.sdm.ecomileage.screens.myHistoryPage

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.myPage.mileageHistoryItem
import com.sdm.ecomileage.ui.theme.*

@Composable
fun MyHistoryScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    point: Int?
) {

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "마일리지 사용 내역",
                currentScreen = SecomiScreens.MyHistoryScreen.name,
                navController = navController,
                backgroundColor = listOf(BasicBackgroundColor, BasicBackgroundColor),
                navigationIcon = painterResource(id = R.drawable.ic_out),
                contentColor = CardContentColor
            )
        },
        backgroundColor = BasicBackgroundColor
    ) {
        MyHistoryLayout(totalPoint = point ?: -200)
    }
}

@Composable
fun MyHistoryLayout(
    totalPoint: Int
) {
    Column(
        modifier = Modifier
            .background(BasicBackgroundColor)
    ) {
        CurrentMileage(totalPoint)

        Spacer(modifier = Modifier.height(20.dp))

        History()
    }
}

@Composable
private fun History(
    myHistoryPageViewModel: MyHistoryPageViewModel = hiltViewModel()
) {

    val componentSize = 55.dp
    var currentType by remember { mutableStateOf("A") }
    var currentPeriod by remember { mutableStateOf(1) }

    val pager = myHistoryPageViewModel.getPager(mileType = currentType, searchMonth = currentPeriod)
        .collectAsLazyPagingItems()

    Log.d("MyHistoryScreen", "History: $pager")


    HistorySorting(
        componentSize,
        currentType,
        { currentType = it },
        currentPeriod,
        { currentPeriod = it }
    )

    Divider(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(vertical = 20.dp),
        color = PlaceholderColor,
        thickness = 0.3.dp
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = 65.dp)
    ) {
        items(pager) {
            it?.let {
                mileageHistoryItem(
                    tintColor = if (it.miletype == "U") UsedMileageColor else IconTintColor,
                    history = it.milereason,
                    period = it.miledate,
                    type = it.miletype != "U",
                    point = it.mileagepoint
                )
            }
        }
        
        if (pager.itemCount == 0) {
            item {
                Text(text = "사용 내역이 없습니다.")
            }
        }
    }
}

@Composable
private fun HistorySorting(
    componentSize: Dp,
    currentType: String,
    onChangeType: (String) -> Unit,
    currentPeriod: Int,
    onChangePeriod: (Int) -> Unit
) {
    var isClickedPeriod by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    Box() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Surface(
                    modifier = Modifier
                        .width(componentSize)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onChangeType("A") },
                    shape = RoundedCornerShape(45),
                    border = if (currentType == "A") BorderStroke(
                        1.dp,
                        PlaceholderColor
                    ) else null,
                    color = BasicBackgroundColor
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "전체",
                            color = PlaceholderColor,
                            letterSpacing = 1.2.sp,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    modifier = Modifier
                        .width(componentSize)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onChangeType("S") },
                    shape = RoundedCornerShape(45),
                    border = if (currentType == "S") BorderStroke(
                        1.dp,
                        PlaceholderColor
                    ) else null,
                    color = BasicBackgroundColor
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "적립",
                            color = PlaceholderColor,
                            letterSpacing = 1.2.sp,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    modifier = Modifier
                        .width(componentSize)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onChangeType("U") },
                    shape = RoundedCornerShape(45),
                    border = if (currentType == "U") BorderStroke(
                        1.dp,
                        PlaceholderColor
                    ) else null,
                    color = BasicBackgroundColor
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "사용",
                            color = PlaceholderColor,
                            letterSpacing = 1.2.sp,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .width(componentSize + 30.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { isClickedPeriod = true },
                shape = RoundedCornerShape(45),
                border = BorderStroke(1.dp, PlaceholderColor),
                color = BasicBackgroundColor
            ) {
                Box() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$currentPeriod 개월",
                            color = PlaceholderColor,
                            letterSpacing = 1.2.sp,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    }


                    DropdownMenu(
                        expanded = isClickedPeriod,
                        onDismissRequest = { isClickedPeriod = false },
                    ) {
                        DropdownMenuItem(onClick = {
                            onChangePeriod(1)
                            isClickedPeriod = false
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = currentPeriod == 1,
                                    onClick = { onChangePeriod(1); isClickedPeriod = false },
                                    modifier = Modifier.size(8.dp),
                                    colors = RadioButtonDefaults.colors(selectedColor = LoginButtonColor)
                                )
                                Text(text = "1 개월")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            onChangePeriod(3)
                            isClickedPeriod = false
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = currentPeriod == 3,
                                    onClick = { onChangePeriod(3); isClickedPeriod = false },
                                    modifier = Modifier.size(8.dp),
                                    colors = RadioButtonDefaults.colors(selectedColor = LoginButtonColor)
                                )
                                Text(text = "3 개월")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            onChangePeriod(6)
                            isClickedPeriod = false
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = currentPeriod == 6,
                                    onClick = { onChangePeriod(6); isClickedPeriod = false },
                                    modifier = Modifier.size(8.dp),
                                    colors = RadioButtonDefaults.colors(selectedColor = LoginButtonColor)
                                )
                                Text(text = "6 개월")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            onChangePeriod(12)
                            isClickedPeriod = false
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = currentPeriod == 12,
                                    onClick = { onChangePeriod(12); isClickedPeriod = false },
                                    modifier = Modifier.size(8.dp),
                                    colors = RadioButtonDefaults.colors(selectedColor = LoginButtonColor)
                                )
                                Text(text = "12 개월")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun CurrentMileage(totalPoint: Int) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(17.dp),
        elevation = 11.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "현재 마일리지",
                    color = PlaceholderColor,
                    letterSpacing = 1.25.sp,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_mileage),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = totalPoint.toString(),
                    color = CardContentColor,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    fontSize = 25.sp,
                    maxLines = 1
                )
                Text(
                    text = "  EP",
                    color = CardContentColor,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    fontSize = 20.sp,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
