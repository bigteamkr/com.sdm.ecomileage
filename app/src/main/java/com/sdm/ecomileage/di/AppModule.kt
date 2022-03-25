package com.sdm.ecomileage.di

import com.sdm.ecomileage.network.CommentAPI
import com.sdm.ecomileage.network.HomeInfoAPI
import com.sdm.ecomileage.network.LoginRegisterFindAPI
import com.sdm.ecomileage.utils.Constants
import com.google.gson.GsonBuilder
import com.sdm.ecomileage.network.SearchAPI
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

    val gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideLoginRegisterFindAPI(): LoginRegisterFindAPI =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(LoginRegisterFindAPI::class.java)

    @Provides
    @Singleton
    fun provideSearchAPI(): SearchAPI =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchAPI::class.java)
}