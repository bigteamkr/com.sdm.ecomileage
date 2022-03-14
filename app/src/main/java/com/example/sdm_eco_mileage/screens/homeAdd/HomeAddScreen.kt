package com.example.sdm_eco_mileage.screens.homeAdd

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.HomeAddSampleData
import com.example.sdm_eco_mileage.data.SampleHomeAdd
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.AddIconBackgroundColor
import com.example.sdm_eco_mileage.ui.theme.NavGreyColor
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

@Composable
private fun HomeAddScaffold(
    navController: NavController,
    sample: SampleHomeAdd
) {
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
        Column {
            HomeAddedImagedRow()
        }


        HomeAddImage()
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

@Preview
@Composable
private fun HomeAddedImagedRow(sample: SampleHomeAdd = HomeAddSampleData) {

    val imageList = remember {
        mutableStateListOf<String>()
    }



    Column() {

        Row(

        ) {
            if (imageList.isEmpty())
                AddImageIcon()

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                itemsIndexed(imageList) { index, url ->
                    when (index) {
                        imageList.lastIndex -> AddImageIcon()
                        else -> uploadedImageCard(url)
                    }
                }
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
fun AddImageIcon() {
    Surface(
        modifier = Modifier.size(150.dp),
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
                modifier = Modifier.size(75.dp)
            )
        }
    }
}