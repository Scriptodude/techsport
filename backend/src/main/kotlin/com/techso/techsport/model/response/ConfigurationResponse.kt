package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.techso.techsport.model.ApplicationConfiguration
import com.techso.techsport.model.strava.ActivityType

class ConfigurationResponse(
    @get:JsonUnwrapped val configuration: ApplicationConfiguration,
    @get:JsonIgnore val language: String) {
    @JsonProperty("supportedActivities")
    val supportedActivities = ActivityType.values().associate { Pair(it, if (language == "en") it.english else it.french) }
}