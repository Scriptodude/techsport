package com.techso.techsport.service.activity

import com.techso.techsport.model.Time
import com.techso.techsport.model.strava.response.Activity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service("time")
class TimeActivityManager : ActivityManager {
    override fun filterActivities(activities: List<Activity>) =
        activities.filter { it.movingTime >= 15 * 60 }

    override fun calculatePoints(activity: Activity) = BigDecimal.valueOf(Time(activity.movingTime).timeInSeconds).setScale(2, RoundingMode.HALF_UP)

    override fun getAppliedRate(activity: Activity): BigDecimal {
        return BigDecimal.ONE
    }
}