package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.search.request.SearchFeedRequest
import com.sdm.ecomileage.model.search.response.SearchFeedResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SearchAPI {
    @POST("SearchFeedInfo")
    suspend fun getSearchedFeed(
        @Header("token") token: String,
        @Body body: SearchFeedRequest
    ) : SearchFeedResponse
}