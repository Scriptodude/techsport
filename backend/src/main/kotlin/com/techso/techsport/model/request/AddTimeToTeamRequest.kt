package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class AddTimeToTeamRequest
@JsonCreator
constructor(
    @JsonProperty("hours")
    val hours: Int,
    @JsonProperty("minutes")
    val minutes: Int,
    @JsonProperty("seconds")
    val seconds: Int?,
    @JsonProperty("who")
    val who: String,
    @JsonProperty("why")
    val why: String,
    @JsonProperty("athlete")
    val athlete: String)