package com.techso.techsport.service

import com.techso.techsport.client.StravaClient
import com.techso.techsport.model.StravaConfig
import com.techso.techsport.model.exception.UnauthorizedException
import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.request.TokenExchangeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception
import java.net.URI
import javax.servlet.http.HttpServletResponse

@Service
class StravaService
@Autowired
constructor(
    private val stravaClient: StravaClient,
    private val stravaConfig: StravaConfig,
    private val validationService: ValidationService,
    @Value("\${techsport.front.url}") val frontUrl: String,
) {

    fun redirect(response: HttpServletResponse) =
        response.sendRedirect(
            URI.create(
                "${this.stravaConfig.oauthUrl}?" +
                        "client_id=${this.stravaConfig.clientId}&" +
                        "redirect_uri=${this.stravaConfig.redirectUrl}&" +
                        "approval_prompt=auto&" +
                        "response_type=code&" +
                        "scope=read,activity:read"
            )
                .normalize()
                .toString()
        );

    fun handleStravaAuth(authRequest: StravaAuthRequest?, httpResponse: HttpServletResponse) {
        try {
            if (authRequest != null && authRequest.error == null && authRequest.code != null) {
                val response = this.stravaClient.exchangeToken(
                    TokenExchangeRequest(
                        this.stravaConfig.clientId,
                        this.stravaConfig.clientSecret,
                        authRequest.code
                    )
                )

                val clubs = this.stravaClient.getClubs(response.accessToken)
                val techso = clubs.firstOrNull { it.name.toLowerCase() == "techso" }

                if (techso == null) {
                    System.err.println("Not in techso")
                } else {
                    val activities = this.stravaClient.getAthleteActivities(response.accessToken);
                    val athlete = this.stravaClient.getCurrentAthlete(response.accessToken);

                    activities.forEach {
                        this.validationService.addActivity(it, athlete)
                    }
                }
            }
        } catch (e: Exception) {
            httpResponse.sendRedirect("${this.frontUrl}?success=false")
        }

        httpResponse.sendRedirect("${this.frontUrl}?success=true")
    }
}