package com.sdm.ecomileage.model.appMemberInfo.response

data class Result(
    val eventAlarmAgree: Boolean,
    val pointSaveType: String,
    val schoolAlarmAgree: Boolean,
    val townAlarmAgree: Boolean,
    val userAccount: String,
    val userAddress: String,
    val userDept: String,
    val userId: String,
    val userImg: String,
    val userName: String,
    val userPoint: Int,
    val userSSOID: String,
    val userSSOType: String,
    val userSchool: Int,
    val userSchoolName: String,
    val userTown: String,
    val userTownName: String
)