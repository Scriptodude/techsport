package com.techso.techsport.model

import com.techso.techsport.model.strava.ActivityType
import org.springframework.data.annotation.Id
import java.time.Instant

typealias PointModifierMap = MutableMap<ActivityType, Double>

data class ApplicationConfiguration(
    @Id
    val id: Int = 1,
    val appMode: ApplicationMode = ApplicationMode.time,
    val pointModifiers: PointModifierMap = mutableMapOf(),
    val startDate: Instant = Instant.parse("2021-01-01T00:00:00Z"),
    val endDate: Instant = Instant.parse("2021-01-01T00:00:00Z")
)