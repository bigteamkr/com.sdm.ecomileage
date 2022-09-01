package com.sdm.ecomileage.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sdm.ecomileage.model.homeInfo.request.HomeInfo
import com.sdm.ecomileage.model.homeInfo.request.HomeInfoRequest
import com.sdm.ecomileage.model.homeInfo.response.Post
import com.sdm.ecomileage.network.HomeInfoAPI
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentLoginedUserId
import javax.inject.Inject

class MainFeedPagingSource @Inject constructor(private val api: HomeInfoAPI) :
    PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1
        val postPerPager = 20
        val requestBody = HomeInfoRequest(
            HomeInfo = listOf(
                HomeInfo(
                    currentLoginedUserId,
                    postpage = page,
                    postperpage = postPerPager
                )
            )
        )

        return try {
            val response = api.getHomeInfo(accessTokenUtil, requestBody)

            if (response.code == 200) {
                val responseBody = response.result.postList
                Log.d("MainFeedPager", "load: call?")

                LoadResult.Page(
                    data = responseBody,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (responseBody.size < postPerPager) null else page + 1
                )

            } else {
                Log.d("MainFeedPager", "load: load fail : ${Exception().message}")
                LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            Log.d("MainFeedPager", "load: Exception : $e")
            LoadResult.Error(e)
        }
    }
}