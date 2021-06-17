package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.web.bind.annotation.RequestParam

data class StravaAuthRequest
@JsonCreator
constructor(
    @RequestParam("error")
    val error: String?,
    @RequestParam("code")
    val code: String?
)