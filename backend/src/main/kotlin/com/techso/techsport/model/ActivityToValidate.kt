package com.techso.techsport.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import java.time.Instant

data class ActivityToValidate(
    @Id val id: String,
    var athleteFullName: String,
    var activityTime: Time,
    @get:JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    var activityDate: Instant,
    var approved: Boolean? = null,
    var teamName: String? = null
)