package com.sdm.ecomileage.screens.education

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.*
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.education.educationInfo.response.EducationInfoResponse
import com.sdm.ecomileage.model.homeInfo.response.HomeInfoResponse
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.home.HomeViewModel
import com.sdm.ecomileage.ui.theme.*

@Composable
fun EducationScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    educationViewModel: EducationViewModel = hiltViewModel()
) {
    val homeInfo = produceState<DataOrException<HomeInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getHomeInfo()
    }.value

    val educationInfo = produceState<DataOrException<EducationInfoResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = educationViewModel.getEducationVideoList()
    }.value

    SideEffect {
        systemUiController.setStatusBarColor(
            color = StatusBarGreenColor
        )
    }

    if (homeInfo.loading == true || educationInfo.loading == true)
        CircularProgressIndicator()
    else if (homeInfo.data?.result != null && educationInfo.data?.challengeList != null) {
        EducationScaffold(navController, homeInfo, educationInfo)
    }
}

@Composable
private fun EducationScaffold(
    navController: NavController,
    homeInfo: DataOrException<HomeInfoResponse, Boolean, Exception>,
    educationInfo: DataOrException<EducationInfoResponse, Boolean, Exception>
) {
    var pushIcon by remember {
        mutableStateOf(R.drawable.ic_push_off)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SecomiTopAppBar(
                title = homeInfo.data!!.result.userDept,
                navController = navController,
                currentScreen = SecomiScreens.EducationScreen.name,
                backgroundColor = TopBarColor,
                actionIconsList = mapOf(
                    "push" to painterResource(id = pushIcon)
                )
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

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxSize()
        ) {
            itemsIndexed(educationInfo.data!!.challengeList) { index, data ->
                var popUpState by remember {
                    mutableStateOf(false)
                }

                Surface(
                    modifier = Modifier.clickable {
                        popUpState = true
                    }
                ) {
                    MainCardFeed(
                        contentImageList = listOf(data.thumnailurl),
                        profileImage = data.manageprofile,
                        profileName = data.managename,
                        educationTitle = data.title,
                        educationTime = "2:39",
                        reactionIcon = null,
                        reactionData = null,
                        onReactionClick = null,
                        reactionTint = null,
                        likeYN = null,
                        colorIcon = {
                            Row(modifier = Modifier.padding(end = 10.dp)) {
                                CustomIconText(
                                    iconResource = R.drawable.ic_mileage,
                                    contentDescription = "마일리지 포인트",
                                    tintColor = MileageColor,
                                    textData = data.point.toString()
                                )
                            }
                        },
                        otherIcons = null,
                        navController = navController,
                        reportDialogCallAction = null,
                        currentScreen = SecomiScreens.EducationScreen.name,
                        feedNo = data.educationsno,
                        contentText = data.videocontent,
                        hashtagList = null,
                        destinationScreen = null
                    )
                }.let {
                    if (popUpState)
                        CustomEducationVideoDialog(
                            data.videourl,
                            data.thumnailurl,
                            data.point,
                            data.educationsno,
                            data.educationyn,
                            data.manageprofile,
                            data.managename,
                            navController
                        ) {
                            popUpState = false
                        }
                }
                if (index == educationInfo.data!!.challengeList.lastIndex)
                    Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
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
    videoUrl: String,
    thumbnailUrl: String,
    mileagePoint: Int,
    educationNo: Int,
    educationYN: Boolean,
    manageProfile: String,
    manageName: String,
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    dismissEvent: (Unit) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current


    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        this.prepare()
    }
    val styledPlayerView = StyledPlayerView(context)
    val mediaItem = MediaItem.fromUri(videoUrl)
    var currentPlayTime = 0L
    var isEnter = true
    var isBuffering by remember {
        mutableStateOf(false)
    }
    var isEnded by remember{
        mutableStateOf(false)
    }


    exoPlayer.setMediaItem(mediaItem)
    styledPlayerView.player = exoPlayer
    styledPlayerView.useController = false

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

    exoPlayer.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_IDLE -> {
                    isBuffering = false
                    currentPlayTime = exoPlayer.currentPosition
                    Log.d("TAG", "onPlaybackStateChanged: IDLE / position : $currentPlayTime")
                }
                ExoPlayer.STATE_BUFFERING -> {
                    isBuffering = true
                    Log.d("TAG", "onPlaybackStateChanged: BUFFER")

                }
                ExoPlayer.STATE_READY -> {
                    isBuffering = false
                    Log.d("TAG", "onPlaybackStateChanged: READY")
                }
                ExoPlayer.STATE_ENDED -> {
                    isBuffering = false
                    isEnded = true
                    currentPlayTime = exoPlayer.currentPosition
                    showLongToastMessage(context, "영상 시청이 완료되었습니다.")
                    Log.d("TAG", "onPlaybackStateChanged: ENDED / position : $currentPlayTime")
                }
            }
        }
    })


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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.55f)
                        .background(Color.Black),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_out),
                        contentDescription = "영상 화면 나가기",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 10.dp)
                            .clickable {
                                dismissEvent(exoPlayer.release())
                            },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(5.dp))
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
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("영상 길이", color = Color.White, style = MaterialTheme.typography.caption)
                        Text(text = "전체화면 아이콘", color = Color.White, style = MaterialTheme.typography.caption)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.padding(end = 4.dp)) {
                        CardWriterInformation(
                            profileImage = manageProfile,
                            profileName = manageName,
                            reactionIcon = null,
                            reactionData = null,
                            onReactionClick = null,
                            reactionTint = null,
                            likeYN = null,
                            colorIcon = {
                                CustomIconText(
                                    iconResource = R.drawable.ic_mileage,
                                    contentDescription = "마일리지 포인트",
                                    modifier = Modifier.padding(),
                                    tintColor = MileageColor,
                                    textData = mileagePoint.toString()
                                )
                            },
                            otherIcons = mapOf("comment" to R.drawable.ic_comment),
                            navController = null,
                            reportDialogCallAction = null,
                            currentScreen = SecomiScreens.EducationScreen.name,
                            feedNo = null
                        )
                    }
                    Text(
                        text = "소감일기를 작성하시고 포인트를 받아가세요!",
                        modifier = Modifier.padding(top = 20.dp),
                        style = MaterialTheme.typography.caption,
                        color = PlaceholderColor
                    )
                    Column(
                        modifier = Modifier.padding(top = 15.dp)
                    ) {
                        Button(
                            onClick = {
                                dismissEvent(exoPlayer.release())
                                navController.navigate(SecomiScreens.DiaryScreen.name + "/$educationNo") {
                                    launchSingleTop
                                }
                            },
                            enabled = !educationYN && isEnded,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = LoginButtonColor
                            )
                        ) {
                            Text(
                                text = if (educationYN) "이미 소감일기를 작성하였습니다." else if (!isEnded) "영상 시청을 완료해주세요." else "소감일기 쓰기",
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                currentPlayTime = 0L
                                exoPlayer.seekTo(currentPlayTime)
                                exoPlayer.play()
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = UnSelectedTabColor
                            )
                        ) {
                            Text(text = "영상 다시보기", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}