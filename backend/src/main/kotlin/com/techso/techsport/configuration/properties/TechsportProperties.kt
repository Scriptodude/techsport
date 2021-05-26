package com.techso.techsport.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Instant

@ConfigurationProperties(prefix = "techsport")
@ConstructorBinding
data class TechsportProperties(
    val frontUrl: String,
    val endOfEvent: Instant,
    val database: DatabaseProperties,
    val strava: StravaProperties
)