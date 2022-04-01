package com.sdm.ecomileage.screens.education

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.sdm.ecomileage.SdmEcoMileageApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor() : ViewModel() {

    private var _isPlayAgain = false
    fun changePlayAgain(){
        _isPlayAgain = !_isPlayAgain
    }
    fun getPlayAgain(): Boolean = _isPlayAgain
}