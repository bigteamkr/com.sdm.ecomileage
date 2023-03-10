package com.sdm.ecomileage.screens.homeAdd

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.sdm.ecomileage.R
import com.sdm.ecomileage.components.SecomiTopAppBar
import com.sdm.ecomileage.components.showShortToastMessage
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest.NewChallengeInfo
import com.sdm.ecomileage.model.challenge.newChallenge.newChallengeRequest.NewChallengeInfoRequest
import com.sdm.ecomileage.model.homeAdd.request.HomeAddRequest
import com.sdm.ecomileage.model.homeAdd.request.Image
import com.sdm.ecomileage.model.homeAdd.request.NewActivityInfo
import com.sdm.ecomileage.navigation.SecomiScreens
import com.sdm.ecomileage.ui.theme.*
import com.sdm.ecomileage.utils.bitmapToString
import com.sdm.ecomileage.utils.currentLoginedUserId
import com.sdm.ecomileage.utils.currentUUIDUtil
import com.sdm.ecomileage.utils.uploadAlarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeAddScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    selectedCategory: String,
    viewModel: HomeAddViewModel = hiltViewModel()
) {
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White
        )
    }

    BackHandler() {
        navController.navigate(SecomiScreens.HomeScreen.name) {
            popUpTo(SecomiScreens.HomeAddScreen.name) { inclusive = true }
        }
    }

    HomeAddScaffold(navController, selectedCategory, viewModel)
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeAddScaffold(
    navController: NavController,
    selectedCategory: String,
    viewModel: HomeAddViewModel
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

//    var showCategoryDialog by rememberSaveable {
//        mutableStateOf(false)
//    }
//    if (showCategoryDialog)
//        CategoryDialog(
//            selectedCategory,
//            showCategoryDialog = showCategoryDialog
//        ) { category, show ->
//            if (category != null) selectedCategory.value = category
//            showCategoryDialog = show
//        }
    val imageList = remember {
        mutableStateListOf<Bitmap?>(null)
    }

    val inputComment = rememberSaveable {
        mutableStateOf("")
    }

    val contentPlaceholderText = "????????? ??????????????????."
    val tagPlaceholderText = "#??????"

    val keyboardAction = {}

    val tagList = remember {
        mutableStateListOf<String>()
    }

    val tagInputElement = remember {
        mutableStateOf("")
    }

    val imageData = mutableListOf<Image>()

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    var canUploadNetworkStatus by remember { mutableStateOf<Boolean?>(true) }
    var isClickedImage by remember { mutableStateOf(false) }
    var isNotEmptyImageList by remember { mutableStateOf(imageList[0] != null) }
    var isAlarm by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    val tag = when (selectedCategory) {
        "????????????" -> R.drawable.image_daily
        "????????? ?????????" -> R.drawable.image_empty_dish
        "???????????? ?????????" -> R.drawable.image_public_transport
        "?????? ????????? ?????????" -> R.drawable.image_thermos
        "????????? ?????? ?????????" -> R.drawable.image_label_detach
        "???????????? ?????????" -> R.drawable.image_basket
        "???????????? ?????????" -> R.drawable.image_pull_a_plug
        "?????? ????????? ?????????" -> R.drawable.image_empty_bottle
        "??????????????? ?????????" -> R.drawable.image_upcycling
        else -> R.drawable.image_daily
    }

    LaunchedEffect(key1 = isClickedImage) {
        isNotEmptyImageList = imageList[0] != null
    }
    if (isLoading)
        CircularProgressIndicator()

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "??? ??????",
                navigationIcon = painterResource(id = R.drawable.ic_out),
                currentScreen = SecomiScreens.HomeAddScreen.name,
                backgroundColor = listOf(Color.White, Color.White),
                navController = navController,
                contentColor = NavGreyColor
            )
        }
    ) {
        Column() {
            Card(
                modifier = Modifier.padding(15.dp),
                shape = RoundedCornerShape(11.dp),
                elevation = 11.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeAddedImagedRow(
                        imageList,
                        deleteImage = { index ->
                            imageList.removeAt(index)
                            isClickedImage = !isClickedImage
                        },
                        addImage = { bitmap ->
                            if (imageList.lastIndex == 0) {
                                imageList.add(
                                    index = 0, bitmap
                                )
                                isClickedImage = !isClickedImage
                            } else {
                                imageList.add(
                                    index = imageList.lastIndex, bitmap
                                )
                                isClickedImage = !isClickedImage
                            }
                        }
                    )

//                    Spacer(modifier = Modifier.height(40.dp))

//                CategoryField(
//                    selectedCategory,
//                    showCategoryDialog
//                ) { showCategoryDialog = it }
//
//                Spacer(modifier = Modifier.height(20.dp))

                    ContentInputField(
                        inputComment,
                        keyboardAction,
                        contentPlaceholderText,
                        Modifier.height(150.dp),
                    )

//                    Spacer(modifier = Modifier.height(20.dp))

                    TagInputField(
                        tagInputElement,
                        focusRequester,
                        tagList,
                        tagPlaceholderText,
                    )

                    Spacer(modifier = Modifier.height(1.dp))
                }

            }

            //Todo : imageList Upload && scope ?????? ???????????? ?????? ???????????? -- .. ?????? ?????? ????????????????

            Button(
                onClick = {
                    if (!isNotEmptyImageList)
                        showShortToastMessage(context, "????????? ??????????????????.")
                    else if (inputComment.value.length < 20)
                        showShortToastMessage(context, "????????? 20??? ?????? ??????????????????.")
                    else {
                        isAlarm = true
                    }
                },
                enabled = canUploadNetworkStatus ?: true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isNotEmptyImageList) ButtonColor else UnableUploadButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "???????????????", fontSize = 17.sp)
            }
        }
    }

    if (isAlarm) {
        Dialog(
            onDismissRequest = { isAlarm = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .width((configuration.screenWidthDp * 0.75).dp)
                    .height((configuration.screenHeightDp * 0.25).dp),
                shape = RoundedCornerShape(5)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "??????",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text(
                            text = uploadAlarm,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.subtitle1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { isAlarm = false },
                                modifier = Modifier.width(140.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = UnableUploadButtonColor,
                                )
                            ) {
                                Text(
                                    text = "????????????",
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.caption,
                                    color = Color.White
                                )
                            }

                            Button(
                                onClick = {
                                    scope.launch(Dispatchers.IO) {
                                        var startTime = System.currentTimeMillis()

                                        isLoading = true
                                        imageData.clear()

                                        var startImageToList = System.currentTimeMillis()
                                        imageList.forEach { image ->
                                            if (image != null) {
                                                imageData.add(
                                                    Image(
                                                        status = "I",
                                                        type = "jpeg",
                                                        filename = "",
                                                        filesno = 0,
                                                        filedtlsno = 0,
                                                        image = bitmapToString(image)
                                                    )
                                                )
                                                image.recycle()
                                            }
                                        }.let {
                                            Log.d(
                                                "TimeCheck",
                                                "HomeAddScaffold: Image To List Time${System.currentTimeMillis() - startImageToList}"
                                            )

                                            if (selectedCategory == "????????????")
                                                viewModel.postHomeFeedInfo(
                                                    HomeAddRequest(
                                                        NewActivityInfo = listOf(
                                                            NewActivityInfo(
                                                                userid = currentLoginedUserId,
                                                                title = "",
                                                                content = inputComment.value,
                                                                category = "1",
                                                                hashtag = tagList.toList(),
                                                                feedsno = 0,
                                                                imageList = imageData.toList(),
                                                            )
                                                        )
                                                    )
                                                ).let {
                                                    when {
                                                        it.loading == true -> canUploadNetworkStatus =
                                                            false
                                                        it.data?.code == 200 -> {
                                                            Log.d(
                                                                "TimeCheck",
                                                                "HomeAddScaffold: total = ${System.currentTimeMillis() - startTime}"
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                navController.navigate(SecomiScreens.HomeScreen.name) {
                                                                    launchSingleTop
                                                                    popUpTo(SecomiScreens.HomeAddScreen.name) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        it.data?.code != 200 -> {
                                                            canUploadNetworkStatus = true
                                                            Log.d(
                                                                "HomeAdd",
                                                                "HomeAddScaffold: ${it.data?.message}"
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                showShortToastMessage(
                                                                    context,
                                                                    "????????? ??????????????????."
                                                                )
                                                            }
                                                        }
                                                    }
                                                } else {
                                                val categoryNum = when (selectedCategory) {
                                                    "????????? ??????" -> 1
                                                    "????????????" -> 2
                                                    "?????? ?????????" -> 3
                                                    "????????? ??????" -> 4
                                                    "????????????" -> 5
                                                    "????????????" -> 6
                                                    "???????????? ??????" -> 7
                                                    "???????????????" -> 8
                                                    else -> 0
                                                }

                                                viewModel.postNewChallengeInfo(
                                                    NewChallengeInfoRequest(
                                                        NewChallengeInfo = listOf(
                                                            NewChallengeInfo(
                                                                uuid = currentUUIDUtil,
                                                                userid = currentLoginedUserId,
                                                                category = categoryNum.toString(),
                                                                content = inputComment.value,
                                                                hashtag = tagList.toList(),
                                                                imageList = imageData.toList(),
                                                                challengesno = categoryNum
                                                            )
                                                        )
                                                    )
                                                ).let {
                                                    when {
                                                        it.loading == true -> canUploadNetworkStatus =
                                                            false
                                                        it.data?.code != 200 -> {
                                                            canUploadNetworkStatus = true
                                                            Log.d(
                                                                "HomeAdd",
                                                                "HomeAddScaffold: ${it.data?.message}"
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                showShortToastMessage(
                                                                    context,
                                                                    "????????? ??????????????????."
                                                                )
                                                            }
                                                            isLoading = false
                                                        }
                                                        it.data?.code == 200 -> {
                                                            withContext(Dispatchers.Main) {
                                                                navController.navigate(SecomiScreens.HomeScreen.name) {
                                                                    launchSingleTop
                                                                    popUpTo(SecomiScreens.HomeAddScreen.name) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.width(140.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = LoginButtonColor
                                )
                            ) {
                                Text(
                                    text = "??????",
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.caption,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    if (isLoading)
                        Surface(
                            modifier = Modifier
                                .padding(top = (configuration.screenHeightDp * 0.25 * 0.25).dp),
                            color = Color.Transparent
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.background(Color.Transparent),
                                color = LoginButtonColor
                            )
                        }
                }

            }

        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TagInputField(
    tagInputElement: MutableState<String>,
    focusRequester: FocusRequester,
    tagList: SnapshotStateList<String>,
    tagPlaceholderText: String,
) {
    val context = LocalContext.current
    var isTagErase by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(10),
        color = Color.White,
        border = BorderStroke(0.3.dp, PlaceholderColor)
//        elevation = 4.dp
    ) {
        BasicTextField(
            value = tagInputElement.value,
            onValueChange = {
                if (tagList.size >= 5) {
                    showShortToastMessage(context, "????????? 5????????? ????????? ??? ????????????.")
                    return@BasicTextField
                }
                tagInputElement.value = it
            },
            modifier = Modifier
                .fillMaxSize()
                .onKeyEvent {
                    if (it.key.keyCode == Key.Backspace.keyCode) {
                        tagList.removeLastOrNull()
                    }
                    false
                }
                .focusRequester(focusRequester = focusRequester),
            textStyle = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Start
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                if (tagList.size >= 5) {
                    showShortToastMessage(context, "????????? 5????????? ????????? ??? ????????????.")
                    return@KeyboardActions
                }
                tagList.add(tagInputElement.value)
                tagInputElement.value = ""
                focusRequester.requestFocus()
            }
        ) { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AddedTagListRow(tagList)
                Box(
                    modifier = Modifier.padding(top = 5.dp),
                ) {
                    if (tagInputElement.value.isEmpty() && tagList.isEmpty())
                        Text(
                            text = tagPlaceholderText, style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = 15.sp
                            )
                        )

                    innerTextField()
                }
            }
        }

    }
}

@Composable
private fun AddedTagListRow(
    tagList: SnapshotStateList<String>
) {
    LazyRow(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(start = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(tagList) { item ->
            Surface(
                shape = RoundedCornerShape(10),
                color = AddIconBackgroundColor,
                modifier = Modifier
                    .height(30.dp)
                    .padding(top = 2.dp),
                elevation = 4.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "#$item",
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp)
//                            .onKeyEvent {
//                                if (it.key.keyCode == Key.Backspace.keyCode)
//                                    tagList.remove(item)
//                                false
//                            },
                        ,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            if (item != tagList.last())
                Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
fun ContentInputField(
    inputComment: MutableState<String>,
    keyboardAction: () -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
) {
    val lineLimitation = with(LocalDensity.current) {
        (15.sp * 3).toDp()
    }

    Surface(
        shape = RoundedCornerShape(5),
        color = Color.White,
        border = BorderStroke(0.3.dp, PlaceholderColor),
//        elevation = 4.dp
    ) {
        BasicTextField(
            value = inputComment.value,
            onValueChange = { inputComment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(end = lineLimitation)
                .then(modifier),
            textStyle = MaterialTheme.typography.body1,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = { keyboardAction() }),
            maxLines = 6,
        ) { innerTextField ->
            if (inputComment.value.isEmpty())
                Text(
                    text = placeholderText, style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                        fontSize = 15.sp
                    )
                )

            innerTextField()
        }

    }
}

@Composable
private fun CategoryField(
    selectedCategory: MutableState<String>,
    showCategoryDialog: Boolean,
    callCategoryDialog: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable {
                callCategoryDialog(!showCategoryDialog)
            },
        shape = RoundedCornerShape(10),
        color = Color.White,
        border = null,
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = selectedCategory.value,
                color = if (selectedCategory.value == "???????????? ??????") PlaceholderColor else Color.Black,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp,
                textAlign = TextAlign.Start
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown),
                contentDescription = "open category list",
                modifier = Modifier
                    .size(17.dp),
                tint = if (selectedCategory.value == "???????????? ??????") PlaceholderColor else Color.Black
            )
        }
    }
}

//@Composable
//fun CategoryDialog(
//    selectedCategory: MutableState<String>,
//    showCategoryDialog: Boolean,
//    onClick: (String?, Boolean) -> Unit
//) {
//    val items = listOf(
//        "????????????",
//        "????????? ?????????",
//        "???????????? ?????????",
//        "?????? ????????? ?????????",
//        "?????? ?????? ?????????",
//        "???????????? ?????????",
//        "?????? ?????? ?????????",
//        "????????? ?????????",
//        "??????????????? ?????????"
//    )
//
//    Surface(
//        modifier = Modifier
//            .padding(horizontal = 15.dp)
//            .fillMaxSize()
//    ) {
//        DropdownMenu(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    onClick(null, !showCategoryDialog)
//                },
//            expanded = showCategoryDialog,
//            onDismissRequest = {
//                onClick(null, !showCategoryDialog)
//            }
//        ) {
//            items.forEachIndexed { index, item ->
//                DropdownMenuItem(
//                    onClick = {
//                        Log.d("TAG", "CategoryDialog: ")
//                        selectedCategory.value = item
//                        onClick(item, !showCategoryDialog)
//                    },
//                    enabled = true
//                ) {
//                    Text(
//                        text = "${index + 1}. $item",
//                        style = MaterialTheme.typography.body1
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeAddedImagedRow(
    imageList: SnapshotStateList<Bitmap?>,
    deleteImage: (Int) -> Unit,
    addImage: (Bitmap?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val cameraBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
//    val imageCropLauncher =
//        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
//            if (result.isSuccessful) {
//                imageUri.value = result.uriContent
//            } else {
//                val exception = result.error
//            }
//        }
//

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
//            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
//            imageCropLauncher.launch(cropOptions)
            if (uri != null) {
                imageUri.value = uri
            } else {
                showShortToastMessage(context, "?????????????????????.")
                Log.d("HomeAdd", "HomeAddedImagedRow: Something is error")
            }
        }

    val imageCameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                cameraBitmap.value = takenPhoto
                //Todo : API ????????????

            } else showShortToastMessage(context, "????????? ????????? ?????????????????????.")
        }


    val dotIndicatorSize = remember {
        mutableStateOf(imageList.size)
    }

    val pagerState = rememberPagerState()
    var source: ImageDecoder.Source

    LaunchedEffect(key1 = imageUri.value, key2 = cameraBitmap.value) {
        if (imageUri.value != null) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri.value)
                addImage(bitmap.value)
                dotIndicatorSize.value = imageList.size
            } else {
                source = ImageDecoder.createSource(context.contentResolver, imageUri.value!!)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                addImage(bitmap.value)
                dotIndicatorSize.value = imageList.size
            }
        } else if (cameraBitmap.value != null) {
            addImage(cameraBitmap.value)
            dotIndicatorSize.value = imageList.size
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
//        border = BorderStroke(0.3.dp, PlaceholderColor)
//        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                HorizontalPager(
                    count = imageList.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    state = pagerState,
                    itemSpacing = 0.dp,
                    contentPadding = PaddingValues(horizontal = 50.dp)
                    //Todo : ? padding 100 ??? ????????? 1??? ??? ????????? ????????? ???????????? ?
                ) { page ->
                    var dropdownOpen by remember {
                        mutableStateOf(false)
                    }

                    if (imageList[page] == null)
                        Box(
                            contentAlignment = Alignment.Center
                        ){
                            Surface(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(220.dp)
                                    .fillMaxHeight()
                                    .clickable {
                                        dropdownOpen = true
                                    },
                                shape = RoundedCornerShape(10),
                                color = AddIconBackgroundColor,
                                contentColor = Color.White
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_image_add),
                                    contentDescription = "HomeAdd Button",
                                    contentScale = ContentScale.Fit
                                )
                            }

                            DropdownMenu(
                                expanded = dropdownOpen,
                                onDismissRequest = { dropdownOpen = !dropdownOpen }) {
                                DropdownMenuItem(onClick = {
                                    imageCameraLauncher.launch()
                                    dropdownOpen = false
                                }) {
                                    Text(text = "??????????????? ????????????")

                                }
                                DropdownMenuItem(onClick = {
                                    imagePickerLauncher.launch("image/*")
                                    dropdownOpen = false
                                }) {
                                    Text(text = "??????????????? ????????????")

                                }
                            }
                        }

                    else
                        Box(
                            modifier = Modifier
                                .size(220.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Surface(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize(),
                                shape = RoundedCornerShape(10),
                                color = Color.Transparent,
                                contentColor = Color.White
                            ) {
                                Image(
                                    painter = rememberImagePainter(imageList[page]),
                                    contentDescription = "bitmap",
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.ic_cancel_upload),
                                contentDescription = "Cancel Uploading image",
                                modifier = Modifier.clickable {
                                    dotIndicatorSize.value = imageList.size
                                    deleteImage(page)
                                }
                            )
                        }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            DotsIndicator(
                totalDots = dotIndicatorSize.value,
                selectedIndex = pagerState.currentPage,
                selectedColor = indicatorSelectedColor,
                unSelectedColor = indicatorUnSelectedColor
            )
        }
    }

}


@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
    dotsSize: Int = 5,
    dotsSpacing: Int = 2
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(dotsSize.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(dotsSize.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(dotsSpacing.dp))
            }
        }
    }
}

@Composable
private fun uploadedImageCard(url: String) {
    Box(
        modifier = Modifier.size(150.dp),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10),
            color = AddIconBackgroundColor,
            contentColor = Color.White
        ) {
            Image(
                painter = rememberImagePainter(data = url),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun AddImageIcon(
    addEvent: (Bitmap?) -> Unit
) {
    var imageUris = emptyList<Uri?>()
    val context = LocalContext.current
    val bitmapList = remember {
        mutableStateListOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        imageUris = it
    }

    val sources = mutableListOf<ImageDecoder.Source>()

    Surface(
        modifier = Modifier
            .size(110.dp)
            .clickable {
                launcher.launch("image/*")
                imageUris.forEach { uri ->
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmapList.add(
                            MediaStore.Images.Media.getBitmap(
                                context.contentResolver,
                                uri
                            )
                        )
                    } else {
                        sources.add(ImageDecoder.createSource(context.contentResolver, uri!!))
                        sources.forEach { source ->
                            bitmapList.add(ImageDecoder.decodeBitmap(source))
                        }
                    }
                }

                bitmapList.forEach {
                    addEvent(it)
                }
            },
        shape = RoundedCornerShape(10),
        color = AddIconBackgroundColor,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image_add),
                contentDescription = "HomeAdd Button",
                modifier = Modifier
                    .size(75.dp)
            )
        }
    }

}
