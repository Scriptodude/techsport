package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeActivityApprobation
@JsonCreator
constructor(
    @JsonProperty("activityId")
    val activityId: String,
    @JsonProperty("approved")
    val approved: Boolean,
    @JsonProperty("teamName")
    val teamName: String?
)
