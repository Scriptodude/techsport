package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.techso.techsport.model.ApplicationMode
import com.techso.techsport.model.PointModifierMap
import java.time.Instant

data class UpdateConfigurationRequest
@JsonCreator
constructor(
    val mode: ApplicationMode,
    val modifiers: PointModifierMap,
    val startDate: Instant,
    val endDate: Instant
)