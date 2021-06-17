package com.techso.techsport.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.techso.techsport.configuration.client.StravaClient
import com.techso.techsport.configuration.properties.TechsportProperties
import com.techso.techsport.model.DataImport
import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.request.TokenExchangeRequest
import com.techso.techsport.model.strava.response.Athlete
import com.techso.techsport.repository.DataImportRepository
import com.techso.techsport.service.activity.ActivityManagerProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Service
class StravaService
@Autowired
constructor(
    private val stravaClient: StravaClient,
    private val techsport: TechsportProperties,
    private val validationService: ValidationService,
    private val dataImportRepository: DataImportRepository,
    private val configurationService: ConfigurationService,
    private val activityManagerProvider: ActivityManagerProvider,
    private val objectMapper: ObjectMapper
) {

    fun redirect(response: HttpServletResponse, session: HttpSession) =
        if (Instant.now().isAfter(this.configurationService.getConfig().endDate) || Instant.now()
                .isBefore(this.configurationService.getConfig().startDate)
        ) {
            response.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=4")
        } else {
            val redirectUri = URLEncoder.encode(
                this.techsport.strava.url.redirect,
                StandardCharsets.UTF_8
            )

            response.sendRedirect(
                URI.create(
                    "${this.techsport.strava.url.oauth}?" +
                            "client_id=${this.techsport.strava.client.id}&" +
                            "redirect_uri=$redirectUri&" +
                            "approval_prompt=auto&" +
                            "response_type=code&" +
                            "scope=read,activity:read,profile:read_all"
                )
                    .normalize()
                    .toString()
            );
        }

    fun handleStravaAuth(
        authRequest: StravaAuthRequest?,
        httpResponse: HttpServletResponse,
        session: HttpSession
    ) {
        try {
            if (authRequest != null && authRequest.error == null && authRequest.code != null && session.getAttribute(
                    "token"
                ) == null
            ) {
                val response = this.stravaClient.exchangeToken(
                    TokenExchangeRequest(
                        this.techsport.strava.client.id,
                        this.techsport.strava.client.secret,
                        authRequest.code
                    )
                )

                val athlete = this.stravaClient.getCurrentAthlete(response.accessToken);

                val techso = athlete.clubs?.firstOrNull { it.id == 944140L };
                if (techso == null) {
                    httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=1")
                    return
                }
                session.setAttribute("athlete", this.objectMapper.writeValueAsString(athlete))
                session.setAttribute("token", response.accessToken)

            }
        } catch (e: Exception) {
            System.err.println(e)
            httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=3")
            return
        }

        httpResponse.sendRedirect("${this.techsport.frontUrl}/strava")
    }

    fun importActivities(httpResponse: HttpServletResponse, session: HttpSession) {
        try {
            if (session.getAttribute("token") == null || session.getAttribute("athlete") == null) {
                httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=3")
            } else {
                val athlete = this.objectMapper.readValue(session.getAttribute("athlete") as String, Athlete::class.java)
                val token = session.getAttribute("token").toString()

                val lastImport =
                    this.dataImportRepository
                        .findById(athlete.id)
                        .map { it.lastImport }
                        .orElse(this.configurationService.getConfig().startDate)

                if (Instant.now().minusMillis(lastImport.toEpochMilli())
                        .toEpochMilli() <= 15 * 60 * 1000
                ) {
                    httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=2")
                    return
                }

                val activities = this.stravaClient.getAthleteActivities(
                    token,
                    lastImport.toEpochMilli() / 1000
                );
                this.dataImportRepository.save(
                    DataImport(
                        athleteId = athlete.id,
                        Instant.now()
                    )
                )

                val goodActivities = this.activityManagerProvider.filterActivities(activities)
                goodActivities.forEach { this.validationService.addActivity(it, athlete) }

                httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?success=true&count=${goodActivities.size}")
            }
        } catch (e: Exception) {
            httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=3")
            return
        }
    }
}