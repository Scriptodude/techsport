package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.techso.techsport.model.Time
import java.math.BigDecimal
import java.time.LocalDate

data class TimeBasedTeamResponse(
    @JsonIgnore val _name: String,
    @JsonIgnore val timeInSeconds: Long,
    @JsonIgnore val _members: List<String>,
    @JsonIgnore val timeChanges: Map<LocalDate, BigDecimal>
): BaseTeamResponse(_name, _members, timeChanges) {
    val timeTotal = Time(this.timeInSeconds)
    val timeToday = Time(timeChanges.getOrDefault(LocalDate.now(), 0).toLong())
}