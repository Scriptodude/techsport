package com.techso.techsport.service

import com.techso.techsport.configuration.client.StravaClient
import com.techso.techsport.configuration.properties.TechsportProperties
import com.techso.techsport.model.DataImport
import com.techso.techsport.model.request.StravaAuthRequest
import com.techso.techsport.model.strava.request.TokenExchangeRequest
import com.techso.techsport.repository.DataImportRepository
import com.techso.techsport.service.activity.ActivityManagerProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URI
import java.time.*
import javax.servlet.http.HttpServletResponse

@Service
class StravaService
@Autowired
constructor(
    private val stravaClient: StravaClient,
    private val techsport: TechsportProperties,
    private val validationService: ValidationService,
    private val dataImportRepository: DataImportRepository,
    private val configurationService: ConfigurationService,
    private val activityManagerProvider: ActivityManagerProvider
) {

    fun redirect(response: HttpServletResponse) =
        if (Instant.now().isAfter(this.configurationService.getConfig().endDate) || Instant.now()
                .isBefore(this.configurationService.getConfig().startDate)
        ) {
            response.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=4")
        } else {
            response.sendRedirect(
                URI.create(
                    "${this.techsport.strava.url.oauth}?" +
                            "client_id=${this.techsport.strava.client.id}&" +
                            "redirect_uri=${this.techsport.strava.url.redirect}&" +
                            "approval_prompt=auto&" +
                            "response_type=code&" +
                            "scope=read,activity:read,profile:read_all"
                )
                    .normalize()
                    .toString()
            );
        }

    fun handleStravaAuth(authRequest: StravaAuthRequest?, httpResponse: HttpServletResponse) {
        try {

            if (authRequest != null && authRequest.error == null && authRequest.code != null) {
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
                } else {
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
                        response.accessToken,
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
            }
        } catch (e: Exception) {
            System.err.println(e)
            httpResponse.sendRedirect("${this.techsport.frontUrl}/strava?failure=true&reason=3")
            return
        }
    }
}