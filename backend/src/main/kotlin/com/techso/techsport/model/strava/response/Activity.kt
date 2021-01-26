package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Activity(
    @JsonProperty("id") val id: Long,
    @JsonProperty("moving_time") val movingTime: Long,
    @JsonProperty("start_date_local") val startDate: Instant?
)