package com.sdm.ecomileage.fragment

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface APIS {

    @POST("/app/AppLogin")
    fun post_users(
        @Body jsonparams: PostModel
    ): Call<PostResult>


    companion object{
        private const val BASE_URL = "http://api.ecosdm.com"

        fun create(): APIS {
            val gson : Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIS::class.java)
        }
    }
}