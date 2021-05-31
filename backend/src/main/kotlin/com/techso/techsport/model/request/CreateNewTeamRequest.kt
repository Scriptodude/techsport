package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class CreateNewTeamRequest
@JsonCreator
constructor(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("members")
    val members: List<String>)