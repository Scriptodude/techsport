package com.techso.techsport.service

import com.techso.techsport.model.request.AddTimeToTeamRequest
import org.springframework.stereotype.Service

@Service
class TimeService {
    fun toSeconds(request: AddTimeToTeamRequest) =
        request.hours * 3600L +
                request.minutes * 60L +
                (request.seconds?.toLong() ?: 0L)
}