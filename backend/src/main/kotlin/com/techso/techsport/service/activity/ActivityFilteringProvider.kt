package com.techso.techsport.service.activity

import com.techso.techsport.model.ApplicationMode
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.ConfigurationService
import org.springframework.stereotype.Service

@Service
class ActivityFilteringProvider(private val configurationService: ConfigurationService) {
    val filterActivities = when(configurationService.getConfig().appMode) {
        ApplicationMode.time -> TimeActivityFiltering()::filterActivities
        ApplicationMode.distancePerSport -> DistancePerSportActivityFiltering::filterActivities
    }
}