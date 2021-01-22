package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class TeamResponse(
    val name: String,
    @JsonIgnore val timeInSeconds: Long,
    val members: List<String>,
    val timeChanges: Map<LocalDate, Long>
) {
    val hours = timeInSeconds / 3600;
    val minutes = (timeInSeconds - hours * 3600) / 60;
    val seconds = timeInSeconds - hours * 3600 - minutes * 60
}