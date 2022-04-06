package com.sdm.ecomileage.screens.homeDetail

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfo
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentInfo
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.newComment.response.NewCommentResponse
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfo
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.homedetail.loginUser.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.request.ActivityInfo
import com.sdm.ecomileage.model.homedetail.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.homedetail.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.repository.commentRepository.CommentRepository
import com.sdm.ecomileage.utils.accessToken
import com.sdm.ecomileage.utils.currentUUID
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
                        uuid = currentUUID,
                        userid = userid,
                        feedsno = feedNo,
                        commentsno = 0
                    )
                )
            )
        )

    private val _reportingCommentList = mutableListOf<Int>()
    fun getReportingCommentValueFromKey(key: Int) = _reportingCommentList.contains(key)
    fun addReportingComment(reportCommentNo: Int) = _reportingCommentList.add(reportCommentNo)

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

    suspend fun getLoginUserInfo(
        uuid: String
    ): DataOrException<AppMemberInfoResponse, Boolean, Exception> =
        repository.getLoginUserInfo(
            accessToken,
            AppMemberInfoRequest(
                AppMemberInfo = listOf(
                    AppMemberInfo(uuid = uuid)
                )
            )
        )
}