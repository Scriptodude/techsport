package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.techso.techsport.model.Time
import java.time.LocalDate

data class TeamResponse(
    val name: String,
    @JsonIgnore val timeInSeconds: Long,
    val members: List<String>,
    val timeChanges: Map<LocalDate, Long>
) {
    val timeTotal = Time(this.timeInSeconds)
    val timeToday = Time(timeChanges.getOrDefault(LocalDate.now(), 0))
}