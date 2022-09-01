package com.sdm.ecomileage.screens.myHistoryPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sdm.ecomileage.model.myPage.userHistoryInfo.response.Mile
import com.sdm.ecomileage.network.MyPageAPI
import com.sdm.ecomileage.repository.myPageRepository.MyPageRepository
import com.sdm.ecomileage.repository.paging.UserHistoryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyHistoryPageViewModel @Inject constructor(
    private val repository: MyPageRepository,
    private val api: MyPageAPI
) : ViewModel() {

    fun getPager(mileType: String, searchMonth: Int): Flow<PagingData<Mile>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { UserHistoryPagingSource(api, mileType, searchMonth) }
        ).flow.cachedIn(viewModelScope)

        return pager
    }

}