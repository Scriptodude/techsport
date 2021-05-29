package com.techso.techsport.service.activity

import com.techso.techsport.model.ApplicationMode
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.ConfigurationService
import org.springframework.stereotype.Service

@Service
class ActivityManagerProvider(
    private val configurationService: ConfigurationService,
    private val distancePerSportActivityManager: DistancePerSportActivityManager,
    private val timeActivityManager: TimeActivityManager
) : ActivityFiltering, PointsCalculator {

    override fun filterActivities(activities: List<Activity>) =
        when (configurationService.getConfig().appMode) {
            ApplicationMode.distancePerSport -> distancePerSportActivityManager.filterActivities(activities)
            ApplicationMode.time -> timeActivityManager.filterActivities(activities)
        }

    override fun calculatePoints(activity: Activity) =
        when (configurationService.getConfig().appMode) {
            ApplicationMode.distancePerSport -> distancePerSportActivityManager.calculatePoints(activity)
            ApplicationMode.time -> timeActivityManager.calculatePoints(activity)
        }

}