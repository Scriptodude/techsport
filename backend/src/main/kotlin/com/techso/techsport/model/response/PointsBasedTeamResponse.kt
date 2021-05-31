package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.time.LocalDate

data class PointsBasedTeamResponse(
    @JsonIgnore val _name: String,
    @JsonIgnore val points: BigDecimal,
    @JsonIgnore val _members: List<String>,
    @JsonIgnore val pointsChange: Map<LocalDate, BigDecimal>
) : BaseTeamResponse(_name, _members, pointsChange) {
    val pointsTotal = this.points
    val pointsToday = pointsChange.getOrDefault(LocalDate.now(), 0)
}