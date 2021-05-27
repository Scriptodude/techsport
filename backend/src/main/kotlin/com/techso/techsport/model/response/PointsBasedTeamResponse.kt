package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class PointsBasedTeamResponse(
    @JsonIgnore val _name: String,
    @JsonIgnore val points: Double,
    @JsonIgnore val _members: List<String>,
    @JsonIgnore val pointsChange: Map<LocalDate, Double>
) : BaseTeamResponse(_name, _members, pointsChange) {
    val pointsTotal = this.points
    val pointsToday = pointsChange.getOrDefault(LocalDate.now(), 0)
}