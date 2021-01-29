package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateTeamMembers
@JsonCreator
constructor(@JsonProperty("members") val members: List<String>)