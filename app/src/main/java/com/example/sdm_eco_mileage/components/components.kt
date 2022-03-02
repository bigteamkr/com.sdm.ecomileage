package com.example.sdm_eco_mileage.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.navigation.SdmScreens

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