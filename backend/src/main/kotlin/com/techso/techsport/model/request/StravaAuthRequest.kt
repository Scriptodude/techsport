package com.techso.techsport.model.request

import org.springframework.web.bind.annotation.RequestParam

data class StravaAuthRequest(
    @RequestParam("error")
    val error: String?,
    @RequestParam("code")
    val code: String?,
)