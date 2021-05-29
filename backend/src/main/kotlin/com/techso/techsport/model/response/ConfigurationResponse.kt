package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.techso.techsport.model.ApplicationConfiguration
import com.techso.techsport.model.strava.ActivityType

class ConfigurationResponse(
    @get:JsonUnwrapped val configuration: ApplicationConfiguration) {
    @JsonProperty("supportedActivities")
    val supportedActivities = ActivityType.values().associate { Pair(it, it.french) }
}