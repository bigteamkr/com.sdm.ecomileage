package com.sdm.ecomileage.repository.searchRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.search.request.SearchFeedRequest
import com.sdm.ecomileage.network.SearchAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: SearchAPI) {

}