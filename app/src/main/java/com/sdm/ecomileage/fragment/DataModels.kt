package com.sdm.ecomileage.fragment


data class PostModel(
    var id : String? =null ,
    var password : String?=null,
    var uuid : String? ="59a1e164-8f55-4486-b8f9-6362892a94f4"
)

data class PostResult(
    val code: Int,
    val `data`: Data,
    val message: String
)


