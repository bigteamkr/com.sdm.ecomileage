package com.sdm.ecomileage.screens.educationVideo

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.sdm.ecomileage.components.CardWriterInformation
import com.sdm.ecomileage.components.CustomIconText
import com.sdm.ecomileage.components.showLongToastMessage
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.screens.education.EducationViewModel
import com.sdm.ecomileage.ui.theme.LoginButtonColor
import com.sdm.ecomileage.ui.theme.MileageColor
import com.sdm.ecomileage.ui.theme.PlaceholderColor
import com.sdm.ecomileage.ui.theme.UnSelectedTabColor
import com.sdm.ecomileage.utils.LockScreenOrientation
import java.net.URLDecoder

@Composable
fun EducationVideoScreen(
    videoURL: String,
    thumbnailURL: String,
    point: Int,
    educationNo: Int,
    educationYN: Boolean,
    manageProfile: String,
    manageId: String,
    manageName: String,
    navController: NavController,
    educationViewModel: EducationViewModel = hiltViewModel()
) {

    // Todo : 영상 전체화면 - 일단 밑의 코드로는 안됨.

    Log.d("EducationVideoScreen", "EducationVideoScreen: AHHHHHHHHHHHHHHHH")

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = ExoPlayer.Builder(context).build().also { it.prepare() }
    val styledPlayerView = StyledPlayerView(context)

    //Todo : ?? Encode 해서 받은건데, Decode 안해도 되네?
    val mediaItem = MediaItem.fromUri(URLDecoder.decode(videoURL, "UTF-8"))
    var currentPlayTime = 0L

    var isEnter by remember{
        mutableStateOf(true)
    }

    var isBuffering by remember {
        mutableStateOf(false)
    }

    var isEnded by remember {
        mutableStateOf(false)
    }

    var isFullScreen by remember {
        mutableStateOf(false)
    }

    exoPlayer.setMediaItem(mediaItem)
    if (styledPlayerView.player != null) {
        styledPlayerView.player = exoPlayer
        styledPlayerView.useController = false
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    if (isEnter) {
                        exoPlayer.play()
                        isEnter = false
                    } else {
                        exoPlayer.seekTo(currentPlayTime)
                        exoPlayer.prepare()
                    }
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

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    if (!isEnter)
        EducationVideoScaffold(
            exoPlayer = exoPlayer,
            styledPlayerView = styledPlayerView,
            onClickFullScreen = { /*TODO*/ },
            manageProfile = manageProfile,
            manageId = manageId,
            manageName = manageName,
            mileagePoint = point,
            navController = navController,
            educationNo = educationNo,
            educationYN = educationYN,
            isEnded = isEnded,
            currentPlayTime = currentPlayTime,
            onClickCurrentPlayTime = { currentPlayTime = it }
        )
    else {
        exoPlayer.stop()
        exoPlayer.release()
    }
}


@Composable
private fun EducationVideoScaffold(
    exoPlayer: ExoPlayer,
    styledPlayerView: StyledPlayerView,
    onClickFullScreen: () -> Unit,
    manageProfile: String,
    manageId: String,
    manageName: String,
    mileagePoint: Int,
    navController: NavController,
    educationNo: Int,
    educationYN: Boolean,
    isEnded: Boolean,
    currentPlayTime: Long,
    onClickCurrentPlayTime: (Long) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.clickable(false) {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(false) { }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .background(Color.Black)
                    .clickable(false) {},
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Icon(
                    painter = painterResource(id = com.sdm.ecomileage.R.drawable.ic_out),
                    contentDescription = "영상 화면 나가기",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            exoPlayer.stop()
                            exoPlayer.release()
                            navController.popBackStack()
                        },
                    tint = Color.White
                )


                AndroidView(
                    factory = {
                        styledPlayerView
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.87f)
                        .clickable {
                            Log.d(
                                "EducationVideoScreen",
                                "EducationVideoScaffold: ${styledPlayerView.player?.isPlaying} / ${exoPlayer.isPlaying}"
                            )
//                            if (styledPlayerView.player!!.isPlaying) styledPlayerView.player!!.pause() else styledPlayerView.player!!.play()

                            if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                        }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("영상 길이", color = Color.White, style = MaterialTheme.typography.caption)
                    Button(onClick = {
                        styledPlayerView.setControllerOnFullScreenModeChangedListener {

                        }
                    }) {
                        Text(
                            text = "전체화면 아이콘",
                            color = Color.White,
                            style = MaterialTheme.typography.caption
                        )
                    }
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
                        profileId = manageId,
                        profileName = manageName,
                        reactionIcon = null,
                        reactionData = null,
                        onReactionClick = null,
                        reactionTint = null,
                        likeYN = null,
                        colorIcon = {
                            CustomIconText(
                                iconResource = com.sdm.ecomileage.R.drawable.ic_mileage,
                                contentDescription = "마일리지 포인트",
                                modifier = Modifier.padding(),
                                tintColor = MileageColor,
                                textData = mileagePoint.toString()
                            )
                        },
                        otherIcons = mapOf("comment" to com.sdm.ecomileage.R.drawable.ic_comment),
                        navController = navController,
                        reportDialogCallAction = null,
                        currentScreen = SecomiScreens.EducationScreen.name,
                        feedNo = null,
                        isOnEducation = true
                    )
                }
                Text(
                    text = "소감일기를 작성하시고 포인트를 받아가세요!",
                    modifier = Modifier.padding(top = 20.dp),
                    style = MaterialTheme.typography.caption,
                    color = PlaceholderColor
                )
                Column(
                    modifier = Modifier.padding(top = 15.dp, start = 8.dp, end = 8.dp)
                ) {
                    Button(
                        onClick = {
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
                            onClickCurrentPlayTime(0L)
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