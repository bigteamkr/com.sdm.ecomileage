package com.example.sdm_eco_mileage.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

var accessToken: String =
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibmlja25hbWUiOiLstZzqsr3tm4giLCJleHAiOjE2NDc1MTk0OTgsInVzZXJpZCI6ImlhbkBiaWd0ZWFtLmNvLmtyIiwiZW1haWwiOiJpYW5AYmlndGVhbS5jby5rciJ9.IFBJB23r8Fbch7xbNhnpzYyAGXR-Q9kTpBkff0zPBSM"
var userid: String = ""


fun bitmapToString(bitmap: Bitmap): String {

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun stringToBitmap(encodedString: String): Bitmap {

    val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)

    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}