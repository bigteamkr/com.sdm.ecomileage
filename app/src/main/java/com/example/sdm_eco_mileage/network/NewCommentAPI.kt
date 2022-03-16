package com.example.sdm_eco_mileage.network

import com.example.sdm_eco_mileage.model.comment.newComment.request.NewCommentRequest
import com.example.sdm_eco_mileage.model.comment.newComment.response.NewCommentResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface NewCommentAPI {
    @POST(value = "NewCommentInfo")
    suspend fun postNewComment(
        @Header("token") token: String,
        @Body body: NewCommentRequest
    ): NewCommentResponse
}