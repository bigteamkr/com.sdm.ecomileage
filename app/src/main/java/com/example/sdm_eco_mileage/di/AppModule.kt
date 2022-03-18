package com.example.sdm_eco_mileage.di

import com.example.sdm_eco_mileage.network.CommentAPI
import com.example.sdm_eco_mileage.network.HomeInfoAPI
import com.example.sdm_eco_mileage.network.LoginRegisterFindAPI
import com.example.sdm_eco_mileage.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHomeInfoApi(): HomeInfoAPI =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeInfoAPI::class.java)


    //Todo : ? 이거 이상한 거 같아 다시 확인!!
//    @Provides
//    @Singleton
//    fun provideGetProfileApi(): String =
//        Retrofit
//            .Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(String::class.java)
//

    @Provides
    @Singleton
    fun provideCommentAPI(): CommentAPI =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentAPI::class.java)


    @Provides
    @Singleton
    fun provideLoginRegisterFindAPI(): LoginRegisterFindAPI =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginRegisterFindAPI::class.java)
}