package com.sdm.ecomileage.screens.education

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.CardWriterInformation
import com.sdm.ecomileage.components.CustomIconText
import com.sdm.ecomileage.data.EducationSampleData
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.LoginButtonColor
import com.sdm.ecomileage.ui.theme.MileageColor
import com.sdm.ecomileage.ui.theme.PlaceholderColor
import com.sdm.ecomileage.ui.theme.UnSelectedTabColor

@Composable
fun EducationScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    educationViewModel: EducationViewModel = hiltViewModel()
) {
    val sample = EducationSampleData
    val popUpState = remember {
        mutableStateOf(false)
    }

    var testDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            testDialog = !testDialog
        }) {
            Text(text = "dialog test btn")
        }
    }


    if (testDialog)
        CustomEducationVideoDialog {
            testDialog = false
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

//@Composable
//fun JWPlayerAndroidView(jwPlayerLicenseUtil: Unit, popUpState: MutableState<Boolean>) {
//    Box {
//        val popupWidth = 250.dp
//        val popupHeight = 250.dp
//        val cornerSize = 16.dp
//
//        Popup(
//            alignment = Alignment.Center,
////            offset = IntOffset(400, 400),
//            onDismissRequest = { popUpState.value = false }
//        ) {
//            // Draw a rectangle shape with rounded corners inside the popup
//            Box(
//                Modifier
//                    .size(popupWidth, popupHeight)
//                    .background(Color.White, RoundedCornerShape(cornerSize)),
//                contentAlignment = Alignment.Center
//            ) {
//                AndroidView(
//                    factory = { context ->
//                        LinearLayout(context).apply {
//                            orientation = LinearLayout.VERTICAL
//                            addView(JWPlayerView(context))
//                        }
//                    },
//                    modifier = Modifier.fillMaxSize(),
//                    update = { layout ->
//                        layout.forEach { view ->
//
//                            val mPlayer = (view as JWPlayerView).player
//
//                            val playListItem = PlaylistItem.Builder()
//                                .file("https://cdn.jwplayer.com/videos/da0Q0zFE-PaXzRVFf.mp4")
//                                .build()
//
//                            val playlist = ArrayList<PlaylistItem>()
//                            playlist.add(playListItem)
//
//                            val config = PlayerConfig.Builder()
//                                .playlist(playlist)
//                                .build()
//
//                            mPlayer.setup(config)
//                        }
//                    }
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomEducationVideoDialog(
    dismissEvent: (Unit) -> Unit
) {
    val configuration = LocalConfiguration.current
    var isPlayAgain by remember{
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.9).dp)
                .height((configuration.screenHeightDp * 0.65).dp),
            shape = RoundedCornerShape(5)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                VideoPlayer(dismissEvent = dismissEvent, isPlayAgain = isPlayAgain)
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardWriterInformation(
                        profileImage = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
                        profileName = "환경 동영상",
                        reactionIcon = null,
                        reactionData = null,
                        onReactionClick = null,
                        reactionTint = null,
                        likeYN = null,
                        colorIcon = {
                            CustomIconText(
                                iconResource = R.drawable.ic_mileage,
                                contentDescription = "마일리지 포인트",
                                tintColor = MileageColor,
                                textData = "25"
                            )
                        },
                        otherIcons = mapOf("comment" to R.drawable.ic_comment),
                        navController = null,
                        reportDialogCallAction = null,
                        currentScreen = SecomiScreens.EducationScreen.name,
                        feedNo = null
                    )
                    Text(
                        text = "소감일기를 작성하시고 포인트를 받아가세요!",
                        style = MaterialTheme.typography.caption,
                        color = PlaceholderColor
                    )
                    Column() {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = LoginButtonColor
                            )
                        ) {
                            Text(text = "소감일기 쓰기")
                        }
                        Button(
                            onClick = { isPlayAgain = !isPlayAgain },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = UnSelectedTabColor
                            )
                        ) {
                            Text(text = "영상 다시보기")
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun VideoPlayer(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    dismissEvent: (Unit) -> Unit,
    isPlayAgain: Boolean
) {
    val context = LocalContext.current
    val sampleVideo = "https://content.jwplatform.com/videos/iIv8qVM0-55ZxFnvI.mp4"
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        this.prepare()
    }
    val styledPlayerView = StyledPlayerView(context)
    val mediaItem = MediaItem.fromUri(sampleVideo)
    var currentPlayTime = 0L
    var isEnter = true


    exoPlayer.setMediaItem(mediaItem)
    styledPlayerView.player = exoPlayer
    styledPlayerView.useController = false

    LaunchedEffect(key1 = isPlayAgain) {
        exoPlayer.stop()
        exoPlayer.play()
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    if (isEnter) {
                        exoPlayer.play()
                        isEnter = false
                    }
                    exoPlayer.seekTo(currentPlayTime)
                    exoPlayer.prepare()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    currentPlayTime = exoPlayer.currentPosition
                    exoPlayer.stop()
                }
                Lifecycle.Event.ON_STOP -> {
                    currentPlayTime = exoPlayer.currentPosition
                    exoPlayer.stop()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    styledPlayerView.player = null
                    exoPlayer.release()
                }
                else -> {
                    Log.d("EducationScreen", "VideoPlayer: Lifecycle ...")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            currentPlayTime = exoPlayer.currentPosition
            exoPlayer.stop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f)
            .background(Color.Black),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_out),
            contentDescription = "영상 화면 나가기",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 10.dp, top = 15.dp)
                .clickable {
                    dismissEvent(exoPlayer.release())
                },
            tint = Color.White
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = {
                    styledPlayerView
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.87f)
                    .clickable {
                        if (styledPlayerView.player!!.isPlaying) styledPlayerView.player!!.pause() else styledPlayerView.player!!.play()
                    }
            )
//            if (showPlayButton)
//                Image(
//                    painter = rememberImagePainter("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E"),
//                    contentDescription = ""
//                )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("2:59", color = Color.White)
            Text(text = "아이콘", color = Color.White)
        }
    }
}