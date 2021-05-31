package com.techso.techsport.model.response

import java.math.BigDecimal
import java.time.LocalDate

abstract class BaseTeamResponse(
    val name: String,
    val members: List<String>,
    val pointChanges: Map<LocalDate, BigDecimal>
)