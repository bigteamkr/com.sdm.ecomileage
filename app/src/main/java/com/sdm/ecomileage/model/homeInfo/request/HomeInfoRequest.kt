package com.sdm.ecomileage.model.homeInfo.request


//Todo : 페이징 해주세용 용 용 용 용용용ㅇ용용용ㅇ :<
data class HomeInfoRequest(
    val HomeInfo: List<HomeInfo>,
    val postpage: Int = 1,
    val postsize: Int = 9999,
    val friendpage: Int = 1,
    val friendperpage: Int = 9999
)