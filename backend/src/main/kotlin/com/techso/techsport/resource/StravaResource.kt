package com.techso.techsport.resource

import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.response.IsStravaAvailableResponse
import com.techso.techsport.service.StravaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("/strava")
@CrossOrigin(
    value = [
        "http://localhost:4200",
        "http://techsport.herokuapp.com",
        "https://techsport.herokuapp.com"],
    allowCredentials = "true"
)
class StravaResource
@Autowired
constructor(
    private val stravaService: StravaService
) {
    @GetMapping
    fun oauthStrava(response: HttpServletResponse, session: HttpSession) {
        this.stravaService.redirect(response, session)
    }

    @GetMapping("/callback")
    fun oauthStrava(
        stravaAuthRequest: StravaAuthRequest,
        response: HttpServletResponse,
        session: HttpSession
    ) {
        this.stravaService.handleStravaAuth(stravaAuthRequest, response, session)
    }

    @GetMapping("/import")
    fun importActivities(response: HttpServletResponse, session: HttpSession) {
        this.stravaService.importActivities(response, session)
    }

    @GetMapping("/available")
    fun isLogged(session: HttpSession) =
        IsStravaAvailableResponse(session.getAttribute("token") != null)
}