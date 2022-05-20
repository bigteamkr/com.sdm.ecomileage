package com.sdm.ecomileage.screens.homeDetail

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfo
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.request.CommentInfoRequest
import com.sdm.ecomileage.model.homedetail.comment.commentInfo.response.CommentInfoResponse
import com.sdm.ecomileage.model.homedetail.comment.deleteComment.DeleteCommentRequest.DeleteCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.deleteComment.DeleteCommentRequest.RemoveCommentInfo
import com.sdm.ecomileage.model.homedetail.comment.deleteComment.DeleteCommentResponse.DeleteCommentResponse
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentInfo
import com.sdm.ecomileage.model.homedetail.comment.newComment.request.NewCommentRequest
import com.sdm.ecomileage.model.homedetail.comment.newComment.response.NewCommentResponse
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfo
import com.sdm.ecomileage.model.homedetail.loginUser.request.AppMemberInfoRequest
import com.sdm.ecomileage.model.homedetail.loginUser.response.AppMemberInfoResponse
import com.sdm.ecomileage.model.homedetail.mainFeed.request.ActivityInfo
import com.sdm.ecomileage.model.homedetail.mainFeed.request.MainFeedRequest
import com.sdm.ecomileage.model.homedetail.mainFeed.response.MainFeedResponse
import com.sdm.ecomileage.model.report.comment.request.NewReportComment
import com.sdm.ecomileage.model.report.comment.request.NewReportCommentRequest
import com.sdm.ecomileage.model.report.comment.response.NewReportCommentResponse
import com.sdm.ecomileage.repository.commentRepository.CommentRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(private val repository: CommentRepository) :
    ViewModel() {

    suspend fun getMainFeed(feedNo: Int): DataOrException<MainFeedResponse, Boolean, Exception> =
        repository.getMainFeed(
            accessTokenUtil,
            MainFeedRequest(
                ActivityInfo = listOf(ActivityInfo(feedNo))
            )
        )

    suspend fun getCommentInfo(
        userid: String,
        feedNo: Int
    ): DataOrException<CommentInfoResponse, Boolean, Exception> =
        repository.getCommentInfo(
            accessTokenUtil,
            CommentInfoRequest(
                CommentInfo = listOf(
                    CommentInfo(
                        lang = "ko",
                        uuid = currentUUIDUtil,
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
    fun deleteReportingComment(reportCommentNo: Int) = _reportingCommentList.remove(reportCommentNo)

    suspend fun postNewComment(
        uuid: String,
        feedNo: Int,
        commentContent: String
    ): DataOrException<NewCommentResponse, Boolean, Exception> =
        repository.postNewComment(
            accessTokenUtil,
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
            accessTokenUtil,
            AppMemberInfoRequest(
                AppMemberInfo = listOf(
                    AppMemberInfo(uuid = uuid)
                )
            )
        )

    suspend fun postNewReportComment(
        feedsno: Int,
        commentsno: Int,
        reportType: String,
        reportContent: String,
        reportyn: Boolean
    ): DataOrException<NewReportCommentResponse, Boolean, Exception> =
        repository.postNewReportComment(
            accessTokenUtil,
            NewReportCommentRequest(
                NewReportComment = listOf(
                    NewReportComment(
                        uuid = currentUUIDUtil,
                        feedsno = feedsno,
                        commentsno = commentsno,
                        reporttype = reportType,
                        reportcontent = reportContent,
                        reportyn = reportyn
                    )
                )
            )
        )

    suspend fun deleteComment(
        commentNo: Int
    ): DataOrException<DeleteCommentResponse, Boolean, Exception> =
        repository.deleteComment(
            accessTokenUtil, DeleteCommentRequest(
                RemoveCommentInfo = listOf(RemoveCommentInfo(commentNo))
            )
        )
}