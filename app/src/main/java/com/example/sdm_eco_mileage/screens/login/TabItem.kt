package com.example.sdm_eco_mileage.screens.login

import androidx.compose.runtime.Composable
import com.example.sdm_eco_mileage.screens.register.RegisterScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title:String, var screen: ComposableFun){
    object Login : TabItem("로그인", { })
    object Register : TabItem("회원가입", { RegisterScreen()})
}
