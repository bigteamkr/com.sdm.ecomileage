package com.example.sdm_eco_mileage.repository.commentRepository

import android.util.Log
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.comment.commentInfo.request.CommentInfoRequest
import com.example.sdm_eco_mileage.model.comment.commentInfo.response.CommentInfoResponse
import com.example.sdm_eco_mileage.model.comment.mainFeed.request.MainFeedRequest
import com.example.sdm_eco_mileage.model.comment.mainFeed.response.MainFeedResponse
import com.example.sdm_eco_mileage.model.comment.newComment.request.NewCommentRequest
import com.example.sdm_eco_mileage.model.comment.newComment.response.NewCommentResponse
import com.example.sdm_eco_mileage.network.CommentAPI
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
}