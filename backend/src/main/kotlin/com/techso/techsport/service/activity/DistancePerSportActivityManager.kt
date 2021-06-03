package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.ConfigurationService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service("distancePerSport")
class DistancePerSportActivityManager(
    private val configurationService: ConfigurationService
) : ActivityManager {
    override fun filterActivities(activities: List<Activity>) =
        activities .filter { it.distance >= 2000 }.map { it.distance = (it.distance / 1000.0); it };

    override fun calculatePoints(activity: Activity) =
        this.getAppliedRate(activity).times(BigDecimal.valueOf(activity.distance))
            .setScale(2, RoundingMode.HALF_UP)

    override fun getAppliedRate(activity: Activity) = BigDecimal.valueOf(
        this.configurationService.getConfig().pointModifiers.getOrDefault(
            activity.type,
            1.0
        )
    );
}