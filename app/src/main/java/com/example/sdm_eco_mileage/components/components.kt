package com.example.sdm_eco_mileage.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.navigation.SdmScreens
import com.example.sdm_eco_mileage.ui.theme.HeartColor

@Composable
fun SdmTopAppBar(
    title: String = "연복중중학교",
    navigationIcon: ImageVector = Icons.Default.ArrowBack,
    currentScreen: String = SdmScreens.HomeScreen.name,
    backgroundColor: Color = Color(0xFF50D989),
    actionIconsList: List<ImageVector> = listOf(
        Icons.Default.Search,
        Icons.Default.Search,
        Icons.Default.Search
    ),
    navController: NavController
) {
    SecomiTopAppBar(backgroundColor, currentScreen, title, actionIconsList)
}

@Composable
fun SecomiTopAppBar(
    backgroundColor: Color,
    currentScreen: String,
    title: String,
    actionIconsList: List<ImageVector>
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left - Title or Icons
            Row(modifier = Modifier.padding(start = 5.dp)) {
                when (currentScreen) {
                    SdmScreens.HomeScreen.name -> {
                        Text(
                            text = title,
                            color = Color.White,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    else -> {

                    }
                }
            }

            //Center title or empty
            Row {
                if (currentScreen != SdmScreens.HomeScreen.name) {
                    Row(modifier = Modifier) {
                        Text(
                            text = title,
                            color = Color.White,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                } else
                    Box {}
            }

            // Right - Icons or empty
            Row(modifier = Modifier.padding(end = 5.dp)) {
                actionIconsList.forEach { icons ->
                    Icon(
                        imageVector = icons, contentDescription = "action icons",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ReactionTwoIcons(firstIcon: ImageVector, secondIcon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Todo: Toggle Icon
        Icon(
            imageVector = firstIcon,
            contentDescription = "",
            tint = HeartColor
        )
        Text(
            text = "23",
            modifier = Modifier.padding(
                start = 2.dp,
                end = 7.dp,
                bottom = 2.dp
            ),
            style = MaterialTheme.typography.caption,
            color = HeartColor
        )

        //Todo : Add Comment Icon image
        Icon(
            imageVector = secondIcon,
            contentDescription = "comment",
            tint = Color.LightGray
        )
    }
}

@Composable
fun ProfileName(name: String, modifier: Modifier = Modifier, fontStyle: TextStyle) {
    Text(
        text = name,
        modifier = modifier
            .width(55.dp)
            .padding(top = 2.dp),
        style = fontStyle,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImage(
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