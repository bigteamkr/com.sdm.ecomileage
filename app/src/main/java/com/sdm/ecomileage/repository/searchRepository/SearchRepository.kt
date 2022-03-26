package com.sdm.ecomileage.repository.searchRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.search.request.SearchFeedRequest
import com.sdm.ecomileage.model.search.response.SearchFeedResponse
import com.sdm.ecomileage.network.SearchAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: SearchAPI) {

    suspend fun getSearchFeedInfo(
        token: String,
        body: SearchFeedRequest
    ): DataOrException<SearchFeedResponse, Boolean, Exception> {
        val response = try {
            api.getSearchedFeed(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d(
                "SearchRepository",
                "getSearchFeedInfo: getSearchFeed: getLogin: api call in repository didn't work"
            )
            Log.d("SearchRepository", "getSearchFeedInfo: getSearchFeed: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}