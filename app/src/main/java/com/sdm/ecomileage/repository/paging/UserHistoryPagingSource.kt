package com.sdm.ecomileage.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sdm.ecomileage.model.myPage.userHistoryInfo.request.AppHistoryInfo
import com.sdm.ecomileage.model.myPage.userHistoryInfo.request.UserHistoryInfoRequest
import com.sdm.ecomileage.model.myPage.userHistoryInfo.response.Mile
import com.sdm.ecomileage.network.MyPageAPI
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import javax.inject.Inject

class UserHistoryPagingSource @Inject constructor(private val api: MyPageAPI, private val mileType: String, private val searchMonth: Int) :
    PagingSource<Int, Mile>() {

    override fun getRefreshKey(state: PagingState<Int, Mile>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Mile> {
        val page = params.key ?: 1
        val perPager = 20
        val requestBody = UserHistoryInfoRequest(
            AppHistoryInfo = listOf(
                AppHistoryInfo(
                    uuid = currentUUIDUtil,
                    page = page,
                    perpage = perPager,
                    mileType = mileType,
                    searchMonths = searchMonth,
                )
            )
        )

        return try {
            val response = api.getUserHistory(accessTokenUtil, requestBody)

            if (response.code == 200) {
                val responseBody = response.result.mileList
                Log.d("UserHistoryInfo", "load: call?")

                LoadResult.Page(
                    data = responseBody,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (responseBody.size < perPager) null else page + 1
                )
            } else {
                Log.d("UserHistoryPager", "load: fail : ${Exception().message}")
                LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            Log.d("UserHistoryPager", "load: Exception : $e")
            LoadResult.Error(e)
        }
    }
}
