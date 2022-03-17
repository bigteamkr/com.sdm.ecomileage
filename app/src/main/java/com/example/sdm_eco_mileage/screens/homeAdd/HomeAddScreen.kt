package com.example.sdm_eco_mileage.screens.homeAdd

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.HomeAddSampleData
import com.example.sdm_eco_mileage.data.SampleHomeAdd
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeAddScreen(
    navController: NavController,
    systemUiController: SystemUiController,
    viewModel: HomeAddViewModel = hiltViewModel()
) {
    val sample = HomeAddSampleData
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White
        )
    }
    HomeAddScaffold(navController, sample)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeAddScaffold(
    navController: NavController,
    sample: SampleHomeAdd
) {
    // Todo : selectedCategory should be in ViewModel
    val selectedCategory = remember {
        mutableStateOf("카테고리 선택")
    }
    val showCategoryDialog = remember {
        mutableStateOf(false)
    }
    if (showCategoryDialog.value)
        CategoryDialog(
            selectedCategory,
            showCategoryDialog = showCategoryDialog.value
        ) { category, show ->
            if (category != null) selectedCategory.value = category
            showCategoryDialog.value = show
        }

    // Todo : inputComment should be in ViewModel
    val inputComment = remember {
        mutableStateOf("")
    }

    val contentPlaceholderText = "내용을 입력해주세요."
    val tagPlaceholderText = "#태그"

    val keyboardAction = {}

    // Todo: TagList should be in ViewModel
    val tagList = remember {
        mutableStateListOf<String>()
    }

    // Todo: TagInputElement should be in ViewModel
    val tagInputElement = remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            SecomiTopAppBar(
                title = "새 활동",
                navigationIcon = painterResource(id = R.drawable.ic_out),
                currentScreen = SecomiScreens.HomeAddScreen.name,
                backgroundColor = listOf(Color.White, Color.White),
                navController = navController,
                contentColor = NavGreyColor
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            ) {
                HomeAddedImagedRow()
                Spacer(modifier = Modifier.height(40.dp))
//                CategoryField(
//                    selectedCategory,
//                    showCategoryDialog.value
//                ) { showCategoryDialog.value = it }
//                Spacer(modifier = Modifier.height(20.dp))
                ContentInputField(inputComment, keyboardAction, contentPlaceholderText)
                Spacer(modifier = Modifier.height(20.dp))
                TagInputField(tagInputElement, focusRequester, tagList, tagPlaceholderText)
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "업로드하기", fontSize = 17.sp)
            }
        }
//        HomeAddImage()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TagInputField(
    tagInputElement: MutableState<String>,
    focusRequester: FocusRequester,
    tagList: SnapshotStateList<String>,
    tagPlaceholderText: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(10),
        color = Color.White,
        elevation = 4.dp
    ) {
        BasicTextField(
            value = tagInputElement.value,
            onValueChange = { tagInputElement.value = it },
            modifier = Modifier
                .fillMaxSize()
                .onKeyEvent {
                    if (it.key.keyCode == Key.Backspace.keyCode && tagInputElement.value.isEmpty())
                        tagList.removeLastOrNull()
                    false
                }
                .focusRequester(focusRequester = focusRequester),
            textStyle = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Start
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                tagList.add("#${tagInputElement.value}")
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

@OptIn(ExperimentalComposeUiApi::class)
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
                        text = item,
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
private fun ContentInputField(
    inputComment: MutableState<String>,
    keyboardAction: () -> Unit,
    placeholderText: String
) {
    Surface(
        shape = RoundedCornerShape(5),
        color = Color.White,
        border = null,
        elevation = 4.dp
    ) {
        BasicTextField(
            value = inputComment.value,
            onValueChange = { inputComment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
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
    val expanded = remember {
        mutableStateOf(showCategoryDialog)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable {
                expanded.value = !expanded.value
                callCategoryDialog(expanded.value)
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
                color = if (selectedCategory.value == "카테고리 선택") PlaceholderColor else Color.Black,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp,
                textAlign = TextAlign.Start
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown),
                contentDescription = "open category list",
                modifier = Modifier
                    .size(17.dp)
            )
        }
    }
}

@Composable
fun CategoryDialog(
    selectedCategory: MutableState<String>,
    showCategoryDialog: Boolean,
    onClick: (String?, Boolean) -> Unit
) {
    val expanded = remember {
        mutableStateOf(showCategoryDialog)
    }

    val items = listOf(
        "일상생활",
        "빈그릇 챌린지",
        "대중교통 챌린지",
        "개인텀블러 챌린지",
        "라벨지 떼기 챌린지",
        "장바구니 챌린지",
        "다회용 용기 챌린지",
        "재활용 챌린지",
        "뚜벅이 챌린지"
    )

    DropdownMenu(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                expanded.value = !expanded.value
                onClick(null, expanded.value)
            },
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = !expanded.value
            onClick(null, expanded.value)
        }
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                onClick = {
                    Log.d("TAG", "CategoryDialog: ")
                    selectedCategory.value = item
                    expanded.value = !expanded.value
                    onClick(item, expanded.value)
                },
                enabled = true
            ) {
                Text(
                    text = "${index + 1}. $item",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Composable
fun HomeAddImage() {
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    Column {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick Image")
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (imageUri.value != null)
        imageUri.value.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it!!)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }

    bitmap.value?.let { btm ->
        Image(
            bitmap = btm.asImageBitmap(), contentDescription = "",
            modifier = Modifier.size(300.dp)
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
private fun HomeAddedImagedRow(sample: SampleHomeAdd = HomeAddSampleData) {
    val imageList = remember {
        mutableStateListOf<Bitmap?>(null)
    }


    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                imageUri.value = result.uriContent
            } else {
                val exception = result.error
            }
        }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
            imageCropLauncher.launch(cropOptions)
        }


    val dotIndicatorSize = remember {
        mutableStateOf(imageList.size)
    }

    val pagerState = rememberPagerState()
    var source: ImageDecoder.Source

    LaunchedEffect(key1 = imageUri.value) {
        if (imageUri.value != null) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri.value)
                if (imageList.lastIndex == 0)
                    imageList.add(0, bitmap.value)
                else
                    imageList.add(imageList.lastIndex, bitmap.value)
                dotIndicatorSize.value = imageList.size
            } else {
                source = ImageDecoder.createSource(context.contentResolver, imageUri.value!!)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                if (imageList.lastIndex == 0)
                    imageList.add(0, bitmap.value)
                else
                    imageList.add(imageList.lastIndex, bitmap.value)
                dotIndicatorSize.value = imageList.size
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            HorizontalPager(
                count = imageList.size,
                state = pagerState,
                itemSpacing = 0.dp
            ) { page ->
                if (imageList[page] == null)
                    Surface(
                        modifier = Modifier
                            .size(200.dp)
                            .clickable {
                                imagePickerLauncher.launch("image/*")
                            },
                        shape = RoundedCornerShape(10),
                        color = AddIconBackgroundColor,
                        contentColor = Color.White
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_image_add),
                            contentDescription = "HomeAdd Button"
                        )
                    }
                else
                    Surface(
                        modifier = Modifier
                            .size(200.dp),
                        shape = RoundedCornerShape(10),
                        color = AddIconBackgroundColor,
                        contentColor = Color.White
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Image(
                                painter = rememberImagePainter(imageList[page]),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxSize(),
                                contentDescription = "bitmap",
                                contentScale = ContentScale.FillBounds
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_cancel_upload),
                                contentDescription = "Cancel Uploading image"
                            )
                        }
                    }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        DotsIndicator(
            totalDots = dotIndicatorSize.value,
            selectedIndex = pagerState.currentPage,
            selectedColor = indicatorSelectedColor,
            unSelectedColor = indicatorUnSelectedColor
        )
    }


    val modifier = Modifier.size(150.dp)
    val scope = rememberCoroutineScope()
}


@Composable
private fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color
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
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(2.dp))
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
                imageUris.forEach() { uri ->
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
