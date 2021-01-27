package com.techso.techsport.service

import com.techso.techsport.client.StravaClient
import com.techso.techsport.model.DataImport
import com.techso.techsport.model.StravaConfig
import com.techso.techsport.model.exception.UnauthorizedException
import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.request.TokenExchangeRequest
import com.techso.techsport.repository.DataImportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception
import java.net.URI
import java.time.*
import javax.servlet.http.HttpServletResponse

@Service
class StravaService
@Autowired
constructor(
    private val stravaClient: StravaClient,
    private val stravaConfig: StravaConfig,
    private val validationService: ValidationService,
    @Value("\${techsport.front.url}") val frontUrl: String,
    private val dataImportRepository: DataImportRepository
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
                    val athlete = this.stravaClient.getCurrentAthlete(response.accessToken);
                    val lastImport =
                        this.dataImportRepository
                            .findById(athlete.id)
                            .map { it.lastImport }
                            .orElse(ZonedDateTime
                                .of(LocalDate.of(2021, 1, 26), LocalTime.MIN, ZoneId.of("UTC"))
                                .toInstant())
                    val activities = this.stravaClient.getAthleteActivities(response.accessToken, lastImport.toEpochMilli() / 1000);
                    this.dataImportRepository.save(DataImport(athleteId = athlete.id, Instant.now()))

                    activities
                        .filter { it.movingTime > 15 * 60 }
                        .forEach { this.validationService.addActivity(it, athlete) }
                }
            }
        } catch (e: Exception) {
            httpResponse.sendRedirect("${this.frontUrl}/strava?failure=true")
        }

        httpResponse.sendRedirect("${this.frontUrl}/strava?success=true")
    }
}