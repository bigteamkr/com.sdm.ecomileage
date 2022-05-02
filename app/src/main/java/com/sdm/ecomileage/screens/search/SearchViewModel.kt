package com.sdm.ecomileage.screens.search

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.search.request.SearchFeedInfo
import com.sdm.ecomileage.model.search.request.SearchFeedRequest
import com.sdm.ecomileage.model.search.response.SearchFeedInfoResponse
import com.sdm.ecomileage.repository.searchRepository.SearchRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    suspend fun getSearchFeedInfo(searchText: String, category: String, order: String): DataOrException<SearchFeedInfoResponse, Boolean, Exception> {
        categoryOrderSelect()

        return repository.getSearchFeedInfo(
            accessTokenUtil,
            SearchFeedRequest(
                listOf(
                    SearchFeedInfo(
                        lang = "ko",
                        uuid = currentUUIDUtil,
                        searchkeyword = searchText,
                        category = category,
                        page = "1",
                        perpage = 10,
                        order = order,
                        desc = "10"
                    )
                )
            )
        )
    }


    private var _searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var _showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _selectedZone: MutableStateFlow<String> = MutableStateFlow("")
    private var _selectedFilter: MutableStateFlow<String> = MutableStateFlow("")
    private var _category: MutableStateFlow<String> = MutableStateFlow("")
    private var _order: MutableStateFlow<String> = MutableStateFlow("")

    private fun categoryOrderSelect() {
        _category.value = when {
            _selectedZone.value == "" && _selectedFilter.value == "" -> "00"
            _selectedZone.value == "동네별" -> "20"
            _selectedZone.value == "학교별" -> "10"
            _selectedFilter.value == "태그" -> "30"
            else -> "00"
        }
        _order.value = when {
            _selectedFilter.value == "인기" -> ""
            _selectedFilter.value == "사용자" -> "user"
            else -> ""
        }
    }


//    private val _searchModelState = combine(
//        _searchText,
//        _showProgressBar
//    ) { text, showProgress ->
//
//        SearchModelState(
//            text,
//            showProgress
//        )
//    }


    var selectedZone = _selectedZone.value
    var selectedFilter = _selectedFilter.value

    fun onSearchTextChanged(changedSearchText: String) {
        _searchText.value = changedSearchText
    }

    fun onSelectedZone(selectedZone: String) {
        _selectedZone.value = selectedZone
    }

    fun onSelectedFilter(selectedFilter: String) {
        _selectedFilter.value = selectedFilter
    }

    fun getOnSelectedZone() = _selectedZone.value
    fun getOnSelectedFilter() = _selectedFilter.value

}