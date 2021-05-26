package com.techso.techsport.model

import com.techso.techsport.model.strava.ActivityType
import org.springframework.data.annotation.Id

typealias PointModifierMap = MutableMap<ActivityType, Double>

data class ApplicationConfiguration(
    @Id
    val id: Int = 1,
    val appMode: ApplicationMode = ApplicationMode.time,
    val pointModifier: PointModifierMap = mutableMapOf()
)