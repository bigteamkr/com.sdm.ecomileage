package com.example.sdm_eco_mileage.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.components.ProfileImage
import com.example.sdm_eco_mileage.components.ProfileName
import com.example.sdm_eco_mileage.components.ReactionTwoIcons
import com.example.sdm_eco_mileage.components.SdmTopAppBar
import com.example.sdm_eco_mileage.data.SampleColumn
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
            HomeMainContent(sampleDataForLazyColumn)
        }
    }
}

@Composable
private fun HomeMainContent(sampleDataForLazyColumn: List<SampleColumn>) {
    LazyColumn(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        items(sampleDataForLazyColumn) { data ->
            HomeMainContentCard(data)
        }
    }
}

@Composable
private fun HomeMainContentCard(data: SampleColumn) {
    Column() {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .padding(end = 2.dp)
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White,
            elevation = 12.dp
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(data = data.image),
                    contentDescription = "Card Content Main Image",
                    modifier = Modifier
                        .fillMaxHeight(0.65f),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        ProfileImage(
                            image = data.image,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        ProfileName(
                            name = data.name,
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontStyle = MaterialTheme.typography.subtitle2
                        )
                    }

                    Row {
                        ReactionTwoIcons(
                            firstIcon = Icons.Default.FavoriteBorder,
                            secondIcon = Icons.Default.Send
                        )

                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "more button",
                            tint = Color.LightGray
                        )
                    }
                }

                Text(
                    text = data.content,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 10.dp,
                        top = 7.dp
                    ),
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
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
                    .padding(start = 5.dp, top = 2.dp)
            ) {
                items(sampleDataForLazyRow) { data ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImage(data.image, Modifier, borderStroke = borderStroke)
                        ProfileName(
                            name = data.name,
                            fontStyle = MaterialTheme.typography.subtitle2
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}


