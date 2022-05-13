package com.sdm.ecomileage.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sdm.ecomileage.network.SearchAPI
import com.sdm.ecomileage.repository.paging.SearchFeedPagingSource
import com.sdm.ecomileage.repository.searchRepository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val api: SearchAPI
) : ViewModel() {

    val pager = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { SearchFeedPagingSource(api, _searchText, _category) }
    ).flow.cachedIn(viewModelScope)

    fun invalidateDataSource() =
        SearchFeedPagingSource(api, _searchText, _category).invalidate()

    private var _searchText = "WEORPZV!@#)$(SAVDZXCVKMASDF"
    private var _showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _selectedZone = ""
    private var _selectedFilter = ""
    private var _category = ""
    private var _order = ""

    fun categoryOrderSelect() {
        _category = when {
            _selectedZone == "" && _selectedFilter == "" -> "00"
            _selectedZone == "동네별" -> "20"
            _selectedZone == "학교별" -> "10"
            _selectedFilter == "태그" -> "30"
            else -> "00"
        }
        _order = when {
            _selectedFilter == "인기" -> ""
            _selectedFilter == "사용자" -> "user"
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


    var selectedZone = _selectedZone
    var selectedFilter = _selectedFilter

    fun onSearchTextChanged(changedSearchText: String) {
        _searchText = changedSearchText
    }

    fun onSelectedZone(selectedZone: String) {
        _selectedZone = selectedZone
    }

    fun onSelectedFilter(selectedFilter: String) {
        _selectedFilter = selectedFilter
    }

    fun getOnSelectedZone() = _selectedZone
    fun getOnSelectedFilter() = _selectedFilter

}