package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service("distancePerSport")
class DistancePerSportActivityManager : ActivityFiltering, PointsCalculator {
    override fun filterActivities(activities: List<Activity>) = activities;
    override fun calculatePoints(activity: Activity): BigDecimal {
        TODO("Not yet implemented")
    }
}