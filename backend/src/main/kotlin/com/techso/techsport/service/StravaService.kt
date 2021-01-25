package com.techso.techsport.service

import com.techso.techsport.client.StravaClient
import com.techso.techsport.model.StravaConfig
import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.request.TokenExchangeRequest
import com.techso.techsport.model.strava.response.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletResponse

@Service
class StravaService(
    @Autowired private val stravaClient: StravaClient,
    @Autowired private val stravaConfig: StravaConfig
) {

    fun redirect(response: HttpServletResponse) =
        response.sendRedirect(URI.create("${this.stravaConfig.oauthUrl}?" +
            "client_id=${this.stravaConfig.clientId}&" +
            "redirect_uri=${this.stravaConfig.redirectUrl}strava/callback&" +
            "approval_prompt=auto&" +
            "response_type=code&" +
            "scope=read,profile:read_all,activity:read_all")
        .normalize()
        .toString());

    fun handleStravaAuth(authRequest: StravaAuthRequest?): List<Activity> {
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
                System.out.println("Null !")
            } else {
                val activities = mutableListOf<Activity>()
                var page = 1
                val today = LocalDate.now().atStartOfDay(ZoneId.systemDefault());

                do {
                    val currentPage = this.stravaClient.getActivitiesOfClub(response.accessToken, 843556, page);
                    if (currentPage.isEmpty()) break;
                    /* else activities.addAll(currentPage.filter {
                        it.startDate != null &&
                        it.startDate.isAfter(today.toInstant()) &&
                                it.startDate.isBefore(today.plusDays(1).toInstant()) }) */

                    page ++
                } while(true)

                return activities
            }
        }

        return listOf()
    }
}