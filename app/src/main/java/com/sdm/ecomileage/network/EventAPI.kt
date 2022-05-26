package com.sdm.ecomileage.network

import com.sdm.ecomileage.model.event.currentEvent.request.AttendanceInfoRequest
import com.sdm.ecomileage.model.event.currentEvent.response.AttendanceInfoResponse
import com.sdm.ecomileage.model.event.newEventComment.request.NewEventCommentRequest
import com.sdm.ecomileage.model.event.newEventComment.response.NewEventCommentResponse
import com.sdm.ecomileage.model.event.newEventJoin.request.NewEventJoinRequest
import com.sdm.ecomileage.model.event.newEventJoin.response.NewEventJoinResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface EventAPI {
    @POST("AttendenceInfo")
    suspend fun getCurrentEvent(
        @Header("token") token: String,
        @Body body: AttendanceInfoRequest
    ): AttendanceInfoResponse

    @POST("NewEventJoinInfo")
    suspend fun postNewEventJoin(
        @Header("token") token: String,
        @Body body: NewEventJoinRequest
    ): NewEventJoinResponse

    @POST("EventCommentInfo")
    suspend fun postNewEventComment(
        @Header("token") token: String,
        @Body body: NewEventCommentRequest
    ): NewEventCommentResponse
}