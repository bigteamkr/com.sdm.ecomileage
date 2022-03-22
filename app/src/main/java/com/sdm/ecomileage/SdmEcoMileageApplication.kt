package com.sdm.ecomileage

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SdmEcoMileageApplication : Application(){
    init{
        instance = this
    }

    companion object {
        lateinit var instance: SdmEcoMileageApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }

}