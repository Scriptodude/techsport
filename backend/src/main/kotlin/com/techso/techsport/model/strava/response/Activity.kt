package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.techso.techsport.model.strava.ActivityType
import java.time.Instant

data class Activity(
    @JsonProperty("id") val id: Long,
    @JsonProperty("moving_time") val movingTime: Long,
    @JsonProperty("start_date_local") val startDate: Instant?,
    @JsonProperty("distance") var distance: Double,
    @JsonProperty("type") val type: ActivityType,
    @JsonProperty("manual") val isManual: Boolean,
    @JsonProperty("total_elevation_gain") val elevationGain: Double
)