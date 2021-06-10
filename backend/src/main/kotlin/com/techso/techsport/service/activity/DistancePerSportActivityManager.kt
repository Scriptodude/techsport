package com.techso.techsport.service.activity

import com.fasterxml.jackson.databind.ObjectMapper
import com.techso.techsport.model.strava.ActivityType
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.service.ConfigurationService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service("distancePerSport")
class DistancePerSportActivityManager(
    private val configurationService: ConfigurationService,
    private val objectMapper: ObjectMapper
) : ActivityManager {
    override fun filterActivities(activities: List<Activity>) =
        activities.filter {
            it.distance >= 2000 && getActivityType(it.stravaType) != null
        }.map {
            it.distance = (it.distance / 1000.0); it.type = getActivityType(it.stravaType)!!; it
        };

    override fun calculatePoints(activity: Activity) =
        this.getAppliedRate(activity).times(BigDecimal.valueOf(activity.distance))
            .setScale(2, RoundingMode.HALF_UP)

    override fun getAppliedRate(activity: Activity) = BigDecimal.valueOf(
        this.configurationService.getConfig().pointModifiers.getOrDefault(
            activity.type,
            1.0
        )
    );

    private fun getActivityType(value: String): ActivityType? =
        ActivityType.values()
            .map { Pair(it, it.name.toLowerCase()) }
            .filter { it.second == value.toLowerCase() }
            .map { it.first }
            .firstOrNull()
}