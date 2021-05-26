package com.techso.techsport.configuration.client

import com.techso.techsport.model.strava.request.TokenExchangeRequest
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.model.strava.response.Athlete
import com.techso.techsport.model.strava.response.Club
import com.techso.techsport.model.strava.response.TokenExchangeResponse
import feign.Headers
import feign.Param
import feign.QueryMap
import feign.RequestLine

interface StravaClient {
    @RequestLine("POST /v3/oauth/token")
    fun exchangeToken(@QueryMap request: TokenExchangeRequest): TokenExchangeResponse

    @RequestLine("GET /v3/athlete/clubs")
    @Headers("Authorization: Bearer {token}")
    fun getClubs(@Param("token") token: String): List<Club>

    @Headers("Authorization: Bearer {token}")
    @RequestLine("GET /v3/athlete/activities?after={after}")
    fun getAthleteActivities(
        @Param("token") token: String,
        @Param("after") after: Long
    ): List<Activity>

    @Headers("Authorization: Bearer {token}")
    @RequestLine("GET /v3/athlete")
    fun getCurrentAthlete(@Param("token") token: String): Athlete
}