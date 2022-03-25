package com.sdm.ecomileage.model.search.viewModel

data class SearchModelState(
    val searchText: String = "",
    val showProgressBar: Boolean = false
) {

    companion object{
        val Empty = SearchModelState()
    }
}