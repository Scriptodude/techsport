package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Club(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("member_count") val memberCount: Int
)