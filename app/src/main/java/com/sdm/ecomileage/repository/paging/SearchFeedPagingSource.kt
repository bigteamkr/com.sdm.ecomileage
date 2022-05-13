package com.sdm.ecomileage.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sdm.ecomileage.model.search.request.SearchFeedInfo
import com.sdm.ecomileage.model.search.request.SearchFeedRequest
import com.sdm.ecomileage.model.search.response.Feed
import com.sdm.ecomileage.network.SearchAPI
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import javax.inject.Inject

class SearchFeedPagingSource @Inject constructor(
    private val api: SearchAPI,
    private val searchKeyword: String,
    private val category: String
) :
    PagingSource<Int, Feed>() {
    override fun getRefreshKey(state: PagingState<Int, Feed>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feed> {
        val page = params.key ?: 1
        val postPerPager = 20
        val requestBody = SearchFeedRequest(
            SearchFeedInfo = listOf(
                SearchFeedInfo(
                    uuid = currentUUIDUtil,
                    searchkeyword = searchKeyword,
                    category = category,
                    page = page,
                    perpage = postPerPager,
                    order = "user",
                    desc = "10"
                )
            )
        )

        return try {
            val response = api.getSearchedFeed(accessTokenUtil, requestBody)

            if (response.code == 200) {
                val responseBody = response.result.feedList
                Log.d("SearchFeedPager", "load: call?")

                LoadResult.Page(
                    data = responseBody,
                    prevKey = if (page == 1) null else -1,
                    nextKey = if (responseBody.size < postPerPager) null else page + 1
                )
            } else {
                Log.d("SearchFeedPager", "load: load fail : ${Exception().message}")
                LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            Log.d("SearchFeedPager", "load: Exception : $e")
            LoadResult.Error(e)
        }
    }
}