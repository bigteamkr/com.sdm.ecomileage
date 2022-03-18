package com.example.sdm_eco_mileage.screens.homeDetail

import androidx.lifecycle.ViewModel
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.comment.commentInfo.request.CommentInfo
import com.example.sdm_eco_mileage.model.comment.commentInfo.request.CommentInfoRequest
import com.example.sdm_eco_mileage.model.comment.commentInfo.response.CommentInfoResponse
import com.example.sdm_eco_mileage.model.comment.mainFeed.request.ActivityInfo
import com.example.sdm_eco_mileage.model.comment.mainFeed.request.MainFeedRequest
import com.example.sdm_eco_mileage.model.comment.mainFeed.response.MainFeedResponse
import com.example.sdm_eco_mileage.model.comment.newComment.request.NewCommentInfo
import com.example.sdm_eco_mileage.model.comment.newComment.request.NewCommentRequest
import com.example.sdm_eco_mileage.model.comment.newComment.response.NewCommentResponse
import com.example.sdm_eco_mileage.repository.commentRepository.CommentRepository
import com.example.sdm_eco_mileage.utils.accessToken
import com.example.sdm_eco_mileage.utils.uuidSample
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

    suspend fun getCommentInfo(userid: String, feedNo: Int) : DataOrException<CommentInfoResponse, Boolean, Exception> =
        repository.getCommentInfo(
            accessToken,
            CommentInfoRequest(
                CommentInfo = listOf(CommentInfo(
                    lang = "ko",
                    uuid = uuidSample,
                    userid = userid,
                    feedsno = feedNo,
                    commentsno = null
                ))
            )
        )

    suspend fun postNewComment(uuid: String, feedNo: Int, commentcontent: String) : DataOrException<NewCommentResponse, Boolean, Exception> =
        repository.postNewComment(
            accessToken,
            NewCommentRequest(
                NewCommentInfo = listOf(NewCommentInfo(
                    uuid = uuid,
                    lang = "ko",
                    feedsno = feedNo,
                    commentsno = null,
                    parentcommentsno = null,
                    commentcontent = commentcontent,
                    commenthashtag = null
                ))
            )
        )
}