package com.techso.techsport.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class IsStravaAvailableResponse(
    @get:JsonProperty("isAvailable") val isAvailable: Boolean = false
)