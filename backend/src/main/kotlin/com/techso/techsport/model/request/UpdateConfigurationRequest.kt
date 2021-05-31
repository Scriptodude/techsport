package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.techso.techsport.model.ApplicationMode
import com.techso.techsport.model.PointModifierMap
import java.time.Instant

data class UpdateConfigurationRequest
@JsonCreator
constructor(
    @JsonProperty("mode")
    val mode: ApplicationMode,
    @JsonProperty("modifiers")
    val modifiers: PointModifierMap,
    @JsonProperty("startDate")
    val startDate: Instant,
    @JsonProperty("endDate")
    val endDate: Instant
)