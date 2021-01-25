package com.techso.techsport.model.strava.request

data class TokenExchangeRequest(
    val client_id: Int,
    val client_secret: String,
    val code: String,
    val grant_type: String = "authorization_code"
)