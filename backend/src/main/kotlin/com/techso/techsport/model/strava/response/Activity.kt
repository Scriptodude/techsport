package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class Activity(
    @JsonProperty("athlete") val athlete: Athlete,
    @JsonProperty("moving_time") val movingTime: Long,
    @JsonProperty("start_date") val startDate: Instant?
)