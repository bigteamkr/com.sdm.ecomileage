package com.sdm.ecomileage.repository.commentRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.newComment.response.NewCommentResponse
import com.sdm.ecomileage.model.report.comment.request.NewReportCommentRequest
import com.sdm.ecomileage.model.report.comment.response.NewReportCommentResponse
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.homedetail.loginUser.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.homedetail.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.network.CommentAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class CommentRepository @Inject constructor(private val api: CommentAPI) {
    suspend fun getMainFeed(
        token: String,
        body: MainFeedRequest
    ): DataOrException<MainFeedResponse, Boolean, Exception> {
        val response = try {
            api.getMainFeed(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("CommentRepo", "getMainFeed: api call in repository didn't work")
            Log.d("CommentRepo", "getMainFeed: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getCommentInfo(
        token: String,
        body: CommentInfoRequest
    ): DataOrException<CommentInfoResponse, Boolean, Exception> {
        val response = try {
            api.getCommentInfo(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("CommentRepo", "getCommentInfo: api call in repository didn't work")
            Log.d("CommentRepo", "getCommentInfo: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun postNewComment(
        token: String,
        body: NewCommentRequest
    ): DataOrException<NewCommentResponse, Boolean, Exception> {
        val response = try {
            api.postNewComment(token = token, body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("CommentRepo", "postNewComment: api call in repository didn't work")
            Log.d("CommentRepo", "postNewComment: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getLoginUserInfo(
        token: String,
        body: AppMemberInfoRequest
    ): DataOrException<AppMemberInfoResponse, Boolean, Exception> {
        val response = try {
            api.getAppMemberInfo(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("CommentRepo", "getLoginUserInfo: api call in repository didn't work")
            Log.d("CommentRepo", "getLoginUserInfo: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun postNewReportComment(
        token: String,
        body: NewReportCommentRequest
    ): DataOrException<NewReportCommentResponse, Boolean, Exception> {
        val response = try {
            api.postNewReportComment(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "CommentRepo",
                "postNewReportComment: postNewReportComment : api call in repository didn't work"
            )
            Log.d("CommentRepo", "postNewReportComment: postNewReportComment : exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}