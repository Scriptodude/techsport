package com.techso.techsport.resource

import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.LoginService
import com.techso.techsport.service.StravaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/strava")
@CrossOrigin(value = [
    "http://localhost:4200",
    "http://techsport.herokuapp.com",
    "https://techsport.herokuapp.com"],
    allowCredentials = "true")
class StravaResource
@Autowired
constructor(
    private val stravaService: StravaService,
    private val loginService: LoginService
) {
    @GetMapping
    fun oauthStrava(
        @CookieValue("token") token: String,
        response: HttpServletResponse) {
        this.loginService.validateToken(token)
        this.stravaService.redirect(response)
    }

    @GetMapping("/callback")
    fun oauthStrava(stravaAuthRequest: StravaAuthRequest, response: HttpServletResponse) {
        this.stravaService.handleStravaAuth(stravaAuthRequest, response)
    }
}