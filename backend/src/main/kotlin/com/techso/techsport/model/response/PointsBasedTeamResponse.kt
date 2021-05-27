package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class PointsBasedTeamResponse(
    @JsonIgnore val _name: String,
    @JsonIgnore val points: Long,
    @JsonIgnore val _members: List<String>,
    @JsonIgnore val timeChanges: Map<LocalDate, Long>
) : BaseTeamResponse(_name, _members, timeChanges) {
    val pointsTotal = this.points
    val pointsToday = timeChanges.getOrDefault(LocalDate.now(), 0)
}