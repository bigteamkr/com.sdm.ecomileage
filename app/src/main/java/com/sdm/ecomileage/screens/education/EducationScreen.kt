package com.sdm.ecomileage.screens.education

import android.widget.LinearLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.core.view.forEach
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.sdm.ecomileage.data.EducationSampleData
import com.sdm.ecomileage.utils.Constants.jwPlayerLicenseKey
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter("https://lh3.googleusercontent.com/NsYBtmF4EoFKWYtBGaiD3A589MTxsbJYbQ27zBHdI3NjpCUWBZAJXkqqLffBSEj8fmdErBGUarEYZhyDf8gw929GUICjlX4hLmDpgihlEA8gGlP4tQYz9JEv-GjZQjWLCMA2B4uk5QB-d4dTOexCa-H1-Jtb-TMdDBAljn0lk59bYicqIksG8hinIWCcCTO37UoB2yuU_LNH0zoAa01RLFMtdQFZzOjEVVBFOkEiTDSlWKv9KamZ7-l_yO1dAXPoAxDzMpOT3eVTIcTJYB0wuZ8WmIyZ4BycJDZPdbR3nRBxBdDdOd8CrLXKPlHHyjJYzKcaoLEtY8qu8Js24aT-4Qe56gdEmwEl6dgRgIue2KcPRrQoIN9XsieX38KpDv5zlXuvRyc6gy4AMxj8mj_pPoZrGWUeIhBO4s2LfnhgT_beQzUIgw49Qz13lfVAMAhb9Qaood5OYd1xJyNw5W9JzB1J8BPITGXHQiZeSyxeq68oNoxo5vRPY8e4F4MDjYLu7jdoZbaAKdh5v4Cc_hME2w_gA0WpTsT3zipr7NH22oQjv8IXTf1KIFkXN6FENdgnWAk3AOiI0M3RW0fcBEKMuvZFZXeGMO1Ur8EaJVhq5BNbRtPZHj4CX8iCFSK_KEUxzpzL92ukIW3aJMtxbOakwAODnX8rLpazVbGVxqDDVg_Wrpqj0qvlLjM_zt9is57ghEpniJ9zJMQ6sNV9k6U0qa759IGaFrDyx3xTpfr0ieELxmbgXJGSbcYGzCg=w293-h650-no?authuser=0"),
            contentDescription = "Sample Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
//
//    Scaffold(
//        topBar = {
//            SecomiTopAppBar(
//                title = "연북중학교",
//                navController = navController,
//                currentScreen = SecomiScreens.EducationScreen.name,
//                backgroundColor = TopBarColor,
//                actionIconsList = listOf(
//                    painterResource(id = R.drawable.ic_push_on)
//                ),
//            )
//        },
//        bottomBar = {
//            SecomiBottomBar(
//                navController = navController,
//                currentScreen = SecomiScreens.EducationScreen.name
//            )
//        },
//        floatingActionButton = { SecomiMainFloatingActionButton(navController) },
//        isFloatingActionButtonDocked = true,
//        floatingActionButtonPosition = FabPosition.Center
//    ) {
//        Box(
//            contentAlignment = Alignment.Center
//        ) {
////            if (popUpState.value)
//            JWPlayerAndroidView(jwPlayerLicenseUtil, popUpState)
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(sample) { item ->
//                    CardContent(
//                        currentScreen = SecomiScreens.EducationScreen.name,
//                        reactionIcon = listOf(R.drawable.ic_like_on, R.drawable.ic_like_on),
//                        reactionData = 20,
//                        onClickReaction = { popUpState.value = true },
//                        reactionTint = MileageColor,
//                        commentIcon = painterResource(id = R.drawable.ic_comment),
//                        needMoreIcon = false,
//                        moreIcon = null,
//                        navController = navController,
//                        destinationScreen = SecomiScreens.HomeDetailScreen.name,
//                        feedNo = 1,
//                        hashtagList = listOf(""),
//                        likeYN = false
//                    )
//                }
//            }
//        }
//    }
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