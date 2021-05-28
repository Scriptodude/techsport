package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity

class TimeActivityFiltering : ActivityFiltering {
    override fun filterActivities(activities: List<Activity>) =
        activities.filter { it.movingTime >= 15 * 60 }
}