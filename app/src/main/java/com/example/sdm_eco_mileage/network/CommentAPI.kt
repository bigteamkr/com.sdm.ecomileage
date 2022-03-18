package com.example.sdm_eco_mileage.network

import com.example.sdm_eco_mileage.model.comment.commentInfo.request.CommentInfoRequest
import com.example.sdm_eco_mileage.model.comment.commentInfo.response.CommentInfoResponse
import com.example.sdm_eco_mileage.model.comment.mainFeed.request.MainFeedRequest
import com.example.sdm_eco_mileage.model.comment.mainFeed.response.MainFeedResponse
import com.example.sdm_eco_mileage.model.comment.newComment.request.NewCommentRequest
import com.example.sdm_eco_mileage.model.comment.newComment.response.NewCommentResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CommentAPI {
    @POST(value = "ActivityInfo")
    suspend fun getMainFeed(
        @Header("token") token:String,
        @Body body : MainFeedRequest
    ) : MainFeedResponse

    @POST(value = "CommentInfo")
    suspend fun getCommentInfo(
        @Header("token") token: String,
        @Body body: CommentInfoRequest
    ) : CommentInfoResponse

    @POST(value = "NewCommentInfo")
    suspend fun postNewComment(
        @Header("token") token: String,
        @Body body: NewCommentRequest
    ): NewCommentResponse
}