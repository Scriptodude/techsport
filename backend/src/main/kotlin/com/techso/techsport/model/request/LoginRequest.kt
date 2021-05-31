package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest
@JsonCreator
constructor(
    @JsonProperty("token")
    val token: String)