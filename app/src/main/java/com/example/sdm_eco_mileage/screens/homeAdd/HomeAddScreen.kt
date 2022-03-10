package com.example.sdm_eco_mileage.screens.homeAdd

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sdm_eco_mileage.R
import com.example.sdm_eco_mileage.components.SecomiTopAppBar
import com.example.sdm_eco_mileage.data.HomeAddSampleData
import com.example.sdm_eco_mileage.data.SampleHomeAdd
import com.example.sdm_eco_mileage.navigation.SecomiScreens
import com.example.sdm_eco_mileage.ui.theme.navGreyColor


@Composable
fun HomeAddScreen(navController: NavController) {
    val sample = HomeAddSampleData
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
                contentColor = navGreyColor
            )
        }
    ) {
        Column() {
            HomeAddedImagedRow(sample)
            HomeAddImage()
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

    Column() {
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
        Image(bitmap = btm.asImageBitmap(), contentDescription = "",
        modifier = Modifier.size(300.dp))
    }
}

@Preview
@Composable
private fun HomeAddedImagedRow(sample: SampleHomeAdd = HomeAddSampleData) {
    Column() {
        LazyRow(
            modifier = Modifier.padding(15.dp)
        ) {
            items(sample.imageList) { url ->
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .height(350.dp)
                        .padding(end = 5.dp),
                    elevation = 12.dp
                ) {
                    Image(
                        painter = rememberImagePainter(data = url), contentDescription = "Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

    }
}
