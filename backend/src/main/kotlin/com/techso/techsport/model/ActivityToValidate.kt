package com.techso.techsport.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.techso.techsport.model.strava.ActivityType
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.Instant

data class ActivityToValidate(
    @Id val id: String,
    var athleteFullName: String,
    var activityTime: Time,
    @get:JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    var activityDate: Instant,
    var distance: BigDecimal?,
    var type: ActivityType?,
    var isManual: Boolean?,
    var points: BigDecimal?,
    var appliedRate: BigDecimal?,
    var approved: Boolean? = null,
    var teamName: String? = null,
)