package com.sdm.ecomileage.screens.homeDetail

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.comment.commentInfo.request.CommentInfo
import com.sdm.ecomileage.model.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.comment.mainFeed.request.ActivityInfo
import com.sdm.ecomileage.model.comment.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.comment.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.comment.newComment.request.NewCommentInfo
import com.sdm.ecomileage.model.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.comment.newComment.response.NewCommentResponse
import com.sdm.ecomileage.repository.commentRepository.CommentRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.uuidSample
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(private val repository: CommentRepository) :
    ViewModel() {

    suspend fun getMainFeed(feedNo: Int): DataOrException<MainFeedResponse, Boolean, Exception> =
        repository.getMainFeed(
            accessToken,
            MainFeedRequest(
                ActivityInfo = listOf(ActivityInfo(feedNo))
            )
        )

    suspend fun getCommentInfo(
        userid: String,
        feedNo: Int
    ): DataOrException<CommentInfoResponse, Boolean, Exception> =
        repository.getCommentInfo(
            accessToken,
            CommentInfoRequest(
                CommentInfo = listOf(
                    CommentInfo(
                        lang = "ko",
                        uuid = uuidSample,
                        userid = userid,
                        feedsno = feedNo,
                        commentsno = 0
                    )
                )
            )
        )

    private val _reportingCommentList = mutableMapOf<Int, String>()
    fun getReportingCommentValueFromKey(key: Int) = _reportingCommentList[key]

    suspend fun postNewComment(
        uuid: String,
        feedNo: Int,
        commentContent: String
    ): DataOrException<NewCommentResponse, Boolean, Exception> =
        repository.postNewComment(
            accessToken,
            NewCommentRequest(
                NewCommentInfo = listOf(
                    NewCommentInfo(
                        uuid = uuid,
                        lang = "ko",
                        feedsno = feedNo,
                        commentsno = null,
                        commentcontent = commentContent,
                        commenthashtag = null
                    )
                )
            )
        )
}