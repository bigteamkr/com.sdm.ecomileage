package com.sdm.ecomileage.screens.ranking

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R.*
import com.sdm.ecomileage.ui.theme.RankingPeriodColor
import com.sdm.ecomileage.ui.theme.RankingTitleColor

@Preview
@Composable
fun RankingScreen(navController: NavController, systemUiController: SystemUiController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(start = 22.dp)){
                BackArrowImage()
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 60.dp)) {
                RankingTitle()
            }
            Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(end = 21.dp)) {
                RankingChangeButton()
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RankingPeriod()
            FirstRankImage()
            TopRankingCard()
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 30.dp)) {
            RankingCard()
        }
    }
}



@Composable
fun BackArrowImage() {
    //Todo : R.drawable.blabla -> drawable.blabla 왜 이런진 모르겠다 ㅠ
    Image(painter = painterResource(id = drawable.ic_back_arrow_black), contentDescription = null)
}

@Composable
fun RankingTitle(){
    Text(text = "마일리지 랭킹", color = RankingTitleColor)
}

@Composable
fun RankingChangeButton(){
    val isSchool = remember{
        mutableStateOf(true)
    }
    Column {
        if(isSchool.value) {
            Image(
                painter = painterResource(id = drawable.ic_ranking_school),
                contentDescription = null,
                modifier = Modifier.clickable { isSchool.value = !isSchool.value }
            )
        }
        else if(!isSchool.value){
            Image(
                painter = painterResource(id = drawable.ic_ranking_address),
                contentDescription = null,
                modifier = Modifier.clickable { isSchool.value = !isSchool.value }
            )
        }
    }
}

@Composable
fun RankingPeriod(){
    Text(text = "2021. 12. 01 ~ 2021. 12. 31", color = RankingPeriodColor)
}

@Composable
fun FirstRankImage(){
    Image(painter = painterResource(id = drawable.ic_gold_medal), contentDescription = null)

}

@Composable
fun TopRankingCard(){
    Column {
        Card(shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(350.dp)
                .height(60.dp)) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column {
                    Text(text = "연북중학교", color = Color.Black)
                }
                Column(modifier = Modifier.padding(top = 5.dp)) {
                    Text(text = "3,456,789 EP", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun RankingCard(){
    Column {
        Card(shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(350.dp)
                .height(420.dp)) {
            Column {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Image(painter = painterResource(id = drawable.ic_silver_medal), contentDescription = null)
                            }
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(text = "연가중학교", color = Color.Black)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                            Text(text = "3,456,776 EP")
                        }
                    }
                }

                Column {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Image(painter = painterResource(id = drawable.ic_bronze_medal), contentDescription = null)
                            }
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(text = "연가중학교", color = Color.Black)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                            Text(text = "3,456,776 EP")
                        }
                    }
                }

                data class SampleRanking(val SchoolName: String, val SchoolPoint: Int, val SchoolImage: Image, val SchoolRanking: Int)

                val Sample = mutableListOf<SampleRanking>()
                LazyColumn{
                    itemsIndexed(Sample){ index, item ->
                        if(index > 2){
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween){
                                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 20.dp)) {
                                    Column(modifier = Modifier.padding(start = 10.dp)) {
                                        Text(text = "4")
                                    }
                                    Column(modifier = Modifier.padding(start = 10.dp)) {
                                        Image(painter = painterResource(id = drawable.ic_kakaotalk), contentDescription = null)
                                    }
                                    Text(text = "응암초등학교", color = Color.Black)
                                }
                                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                                    Text(text = "2,345,678 EP")
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}