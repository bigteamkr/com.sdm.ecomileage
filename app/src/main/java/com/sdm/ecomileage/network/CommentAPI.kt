package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.homedetail.comment.deleteComment.DeleteCommentRequest.DeleteCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.deleteComment.DeleteCommentResponse.DeleteCommentResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.homedetail.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.newComment.response.NewCommentResponse
import com.sdm.ecomileage.model.report.comment.request.NewReportCommentRequest
import com.sdm.ecomileage.model.report.comment.response.NewReportCommentResponse
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.homedetail.loginUser.response.AppMemberInfoResponse
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

    @POST(value = "AppMemberInfo")
    suspend fun getAppMemberInfo(
        @Header("token") token: String,
        @Body body: AppMemberInfoRequest
    ) : AppMemberInfoResponse

    @POST(value = "NewReportComment")
    suspend fun postNewReportComment(
        @Header("token") token: String,
        @Body body: NewReportCommentRequest
    ) : NewReportCommentResponse

    @POST(value = "RemoveCommentInfo")
    suspend fun deleteComment(
        @Header("token") token: String,
        @Body body: DeleteCommentRequest
    ) : DeleteCommentResponse
}