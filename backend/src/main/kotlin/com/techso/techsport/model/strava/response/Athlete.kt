package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Athlete(
    @JsonProperty("id") val id: Long,
    @JsonProperty("firstname") val firstname: String,
    @JsonProperty("lastname") val lastname: String,
    @JsonProperty("clubs") val clubs: List<Club>?
)