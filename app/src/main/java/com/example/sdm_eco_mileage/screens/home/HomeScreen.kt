package com.example.sdm_eco_mileage.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.components.SdmTopAppBar
import com.example.sdm_eco_mileage.data.SampleRow
import com.example.sdm_eco_mileage.data.mainScrollColumnViewData
import com.example.sdm_eco_mileage.data.mainTopScrollRowViewData

@Preview
@Composable
fun HomeScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(
        topBar = { SdmTopAppBar(navController = navController) }
    ) {
        val sampleDataForLazyRow = mainTopScrollRowViewData
        val sampleDataForLazyColumn = mainScrollColumnViewData

        Column() {
            HomeUserFeedRow(sampleDataForLazyRow)


            LazyColumn(
                modifier = Modifier.padding(5.dp)
            ) {
                items(sampleDataForLazyColumn) { data ->
                    Column() {
                        Card(
                            modifier = Modifier
                                .width(350.dp)
                                .height(300.dp),
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = Color.White,
                            elevation = 3.dp
                        ) {
                            Column() {
                                Image(
                                    painter = rememberImagePainter(data = data.image),
                                    contentDescription = "Card Content Main Image",
                                )
                                Row() {
                                    ProfileImage(
                                        image = data.image,
                                        modifier = Modifier.size(30.dp)
                                    )
                                    ProfileName(name = data.name)
                                }
                            }
                        }
                    }
                }
            }


        }
    }
}


@Composable
private fun HomeUserFeedRow(sampleDataForLazyRow: List<SampleRow>) {
    val borderStroke = BorderStroke(width = 2.dp, color = Color.LightGray)

    Surface(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(top = 2.dp)
            ) {
                items(sampleDataForLazyRow) { data ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(data.image, Modifier, borderStroke = borderStroke)
                        ProfileName(data.name, Modifier)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Composable
private fun ProfileName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
            .width(55.dp)
            .padding(top = 2.dp),
        style = MaterialTheme.typography.subtitle2,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileImage(
    image: String,
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(
        width = 0.dp,
        color = Color.LightGray
    )
) {
    Surface(
        onClick = { },
        modifier = modifier.size(55.dp),
        shape = CircleShape,
        border = borderStroke
    ) {
        Image(
            painter = rememberImagePainter(image),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop
        )
    }
}


