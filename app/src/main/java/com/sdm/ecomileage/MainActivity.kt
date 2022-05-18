package com.sdm.ecomileage

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.sdm.ecomileage.navigation.SdmNavigation
import com.sdm.ecomileage.ui.theme.Sdm_eco_mileageTheme
import com.sdm.ecomileage.utils.kakaoNativeAppKey
import com.sdm.ecomileage.utils.naverClientID
import com.sdm.ecomileage.utils.naverClientSecret
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setContent {
            Sdm_eco_mileageTheme {
                // A surface container using the 'background' color from the theme
                
                SecomiApp()

                KakaoSdk.init(this, kakaoNativeAppKey)
                NaverIdLoginSDK.initialize(
                    this,
                    naverClientID,
                    naverClientSecret,
                    "기후환경 마일리지"
                )

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SecomiApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SdmNavigation()
        }
    }
}