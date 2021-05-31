package com.techso.techsport.service.activity

import com.techso.techsport.model.ApplicationMode
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.ConfigurationService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ActivityManagerProvider(
    private val configurationService: ConfigurationService,
    private val distancePerSportActivityManager: DistancePerSportActivityManager,
    private val timeActivityManager: TimeActivityManager
) : ActivityFiltering, PointsCalculator {

    override fun filterActivities(activities: List<Activity>) = this.getActivityManager().filterActivities(activities)
    override fun calculatePoints(activity: Activity) = this.getActivityManager().calculatePoints(activity)
    override fun getAppliedRate(activity: Activity) = this.getActivityManager().getAppliedRate(activity)

    private fun getActivityManager(): ActivityManager = when (configurationService.getConfig().appMode) {
        ApplicationMode.distancePerSport -> distancePerSportActivityManager
        ApplicationMode.time -> timeActivityManager
    }
}