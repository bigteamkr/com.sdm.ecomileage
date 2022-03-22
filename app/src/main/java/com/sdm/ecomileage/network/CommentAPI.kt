package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.comment.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.comment.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.comment.newComment.response.NewCommentResponse
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