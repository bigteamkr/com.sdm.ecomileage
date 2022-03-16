package com.example.sdm_eco_mileage.screens.education

import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.core.view.forEach
import androidx.navigation.NavController
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.CardContent
import com.example.sdm_eco_mileage.components.SecomiBottomBar
import com.example.sdm_eco_mileage.components.SecomiMainFloatingActionButton
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.EducationSampleData
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.MileageColor
import com.example.sdm_eco_mileage.ui.theme.TopBarColor
import com.example.sdm_eco_mileage.utils.Constants.jwPlayerLicenseKey
import com.google.accompanist.systemuicontroller.SystemUiController
import com.jwplayer.pub.api.configuration.PlayerConfig
import com.jwplayer.pub.api.license.LicenseUtil
import com.jwplayer.pub.api.media.playlists.PlaylistItem
import com.jwplayer.pub.view.JWPlayerView

@Composable
fun EducationScreen(navController: NavController, systemUiController: SystemUiController) {
    val sample = EducationSampleData
    val popUpState = remember {
        mutableStateOf(false)
    }
    val jwPlayerLicenseUtil = LicenseUtil().setLicenseKey(LocalContext.current, jwPlayerLicenseKey)

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "연북중학교",
                navController = navController,
                currentScreen = SecomiScreens.EducationScreen.name,
                backgroundColor = TopBarColor,
                actionIconsList = listOf(
                    painterResource(id = R.drawable.ic_push_on)
                ),
            )
        },
        bottomBar = {
            SecomiBottomBar(
                navController = navController,
                currentScreen = SecomiScreens.EducationScreen.name
            )
        },
        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
//            if (popUpState.value)
            JWPlayerAndroidView(jwPlayerLicenseUtil, popUpState)

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(sample) { item ->
                    CardContent(
                        currentScreen = SecomiScreens.EducationScreen.name,
                        reactionIcon = listOf(R.drawable.ic_like_on, R.drawable.ic_like_on),
                        reactionData = 20,
                        onClickReaction = { popUpState.value = true },
                        reactionTint = MileageColor,
                        commentIcon = painterResource(id = R.drawable.ic_comment),
                        needMoreIcon = false,
                        moreIcon = null,
                        navController = navController,
                        destinationScreen = SecomiScreens.HomeDetailScreen.name,
                        feedNo = 1,
                        hashtagList = listOf("")
                    )
                }
            }
        }
    }
}

@Composable
fun JWPlayerAndroidView(jwPlayerLicenseUtil: Unit, popUpState: MutableState<Boolean>) {
    Box {
        val popupWidth = 250.dp
        val popupHeight = 250.dp
        val cornerSize = 16.dp

        Popup(
            alignment = Alignment.Center,
//            offset = IntOffset(400, 400),
            onDismissRequest = { popUpState.value = false }
        ) {
            // Draw a rectangle shape with rounded corners inside the popup
            Box(
                Modifier
                    .size(popupWidth, popupHeight)
                    .background(Color.White, RoundedCornerShape(cornerSize)),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        LinearLayout(context).apply {
                            orientation = LinearLayout.VERTICAL
                            addView(JWPlayerView(context))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { layout ->
                        layout.forEach { view ->

                            val mPlayer = (view as JWPlayerView).player

                            val playListItem = PlaylistItem.Builder()
                                .file("https://cdn.jwplayer.com/videos/da0Q0zFE-PaXzRVFf.mp4")
                                .build()

                            val playlist = ArrayList<PlaylistItem>()
                            playlist.add(playListItem)

                            val config = PlayerConfig.Builder()
                                .playlist(playlist)
                                .build()

                            mPlayer.setup(config)
                        }
                    }
                )
            }
        }
    }
}