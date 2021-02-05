package com.techso.techsport.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import java.time.Instant

open class ManualTimeLog(
    @Id val id: String,
    val whoEntered: String,
    val teamName: String,
    val why: String,
    val athleteName: String,
    val time: Time,
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    val dateTime: Instant
)