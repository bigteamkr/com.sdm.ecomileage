package com.sdm.ecomileage.screens.event

import androidx.lifecycle.ViewModel
import com.sdm.ecomileage.model.event.currentEvent.request.AttendanceInfoRequest
import com.sdm.ecomileage.model.event.currentEvent.request.AttendenceInfo
import com.sdm.ecomileage.model.event.newEventComment.request.NewEventCommentInfo
import com.sdm.ecomileage.model.event.newEventComment.request.NewEventCommentRequest
import com.sdm.ecomileage.model.event.newEventJoin.request.NewEventInfo
import com.sdm.ecomileage.model.event.newEventJoin.request.NewEventJoinRequest
import com.sdm.ecomileage.repository.eventRepository.EventRepository
import com.sdm.ecomileage.utils.accessTokenUtil
import com.sdm.ecomileage.utils.currentLoginedUserId
import com.sdm.ecomileage.utils.currentUUIDUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {
    suspend fun getCurrentEventInfo() = repository.getCurrentEventInfo(
        accessTokenUtil,
        AttendanceInfoRequest(
            listOf(
                AttendenceInfo(
                    uuid = currentUUIDUtil,
                    userid = currentLoginedUserId
                )
            )
        )
    )

    suspend fun postNewEventJoin(eventId: String) = repository.postNewEventJoin(
        accessTokenUtil,
        NewEventJoinRequest(
            listOf(
                NewEventInfo(
                    lang = "ko",
                    uuid = currentUUIDUtil,
                    userid = currentLoginedUserId,
                    eventid = eventId
                )
            )
        )
    )

    suspend fun postNewEventComment(eventId: String, content: String) =
        repository.postNewEventComment(
            accessTokenUtil,
            NewEventCommentRequest(
                listOf(
                    NewEventCommentInfo(
                        uuid = currentUUIDUtil,
                        eventid = eventId,
                        eventcommentsno = 0,
                        eventcommentcontent = content
                    )
                )
            )
        )

}