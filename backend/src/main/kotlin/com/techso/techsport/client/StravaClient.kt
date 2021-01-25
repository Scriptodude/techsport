package com.techso.techsport.client

import com.fasterxml.jackson.databind.JsonNode
import com.techso.techsport.model.strava.request.TokenExchangeRequest
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
    @RequestLine("GET /v3/clubs/{id}/activities?page={page}")
    fun getActivitiesOfClub(
        @Param("token") token: String,
        @Param("id") clubId: Long,
        @Param("page") page: Int = 1
    ): List<JsonNode>
}