package com.sdm.ecomileage.repository.eventRepository

import android.util.Log
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.model.event.currentEvent.request.AttendanceInfoRequest
import com.sdm.ecomileage.model.event.currentEvent.response.AttendanceInfoResponse
import com.sdm.ecomileage.model.event.newEventComment.request.NewEventCommentRequest
import com.sdm.ecomileage.model.event.newEventComment.response.NewEventCommentResponse
import com.sdm.ecomileage.model.event.newEventJoin.request.NewEventJoinRequest
import com.sdm.ecomileage.model.event.newEventJoin.response.NewEventJoinResponse
import com.sdm.ecomileage.network.EventAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class EventRepository @Inject constructor(private val api: EventAPI) {
    suspend fun getCurrentEventInfo(
        token: String,
        body: AttendanceInfoRequest
    ): DataOrException<AttendanceInfoResponse, Boolean, Exception> {
        val response = try {
            api.getCurrentEvent(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("EventRepository", "getEventInfo: api call in repository didn't work")
            Log.d("EventRepository", "getEventInfo: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun postNewEventJoin(
        token: String,
        body: NewEventJoinRequest
    ): DataOrException<NewEventJoinResponse, Boolean, Exception> {
        val response = try {
            api.postNewEventJoin(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("EventRepository", "postNewEventJoin: api call in repository didn't work")
            Log.d("EventRepository", "postNewEventJoin: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun postNewEventComment(
        token: String,
        body: NewEventCommentRequest
    ): DataOrException<NewEventCommentResponse, Boolean, Exception> {
        val response = try {
            api.postNewEventComment(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("EventRepository", "postNewEventComment: api call in repository didn't work")
            Log.d("EventRepository", "postNewEventComment: exception is $e")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}