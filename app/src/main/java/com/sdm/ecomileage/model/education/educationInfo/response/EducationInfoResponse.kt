package com.sdm.ecomileage.model.education.educationInfo.response

data class EducationInfoResponse(
    val challengeList: List<Challenge>,
    val code: Int,
    val message: String
)