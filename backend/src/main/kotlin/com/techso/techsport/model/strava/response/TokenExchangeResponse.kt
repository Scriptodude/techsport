package com.techso.techsport.model.strava.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TokenExchangeResponse
@JsonCreator
constructor(
    @JsonProperty("expires_at")
    val expiresAt: Long,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("athlete")
    val athlete: Athlete?)