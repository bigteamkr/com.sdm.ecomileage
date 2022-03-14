package com.example.sdm_eco_mileage.screens.homeAdd

import android.content.ContentUris
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdm_eco_mileage.SdmEcoMileageApplication
import com.example.sdm_eco_mileage.repository.HomeRepository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.Q)
@HiltViewModel
class HomeAddViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
//    lateinit var thumbnail: Bitmap
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private val _thumbnail: Bitmap = SdmEcoMileageApplication.ApplicationContext().contentResolver.loadThumbnail(
//        MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//        android.util.Size(250, 250),
//        null
//    )
//
//
//
//    init {
//        viewModelScope.launch {
//            callThumbnail()
//        }
//    }
//
//
//    suspend fun callThumbnail(){
//        withContext(Dispatchers.IO){
//            thumbnail = _thumbnail
//        }
//    }

}