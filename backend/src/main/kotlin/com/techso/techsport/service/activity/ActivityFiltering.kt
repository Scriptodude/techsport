package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity

interface ActivityFiltering {
    fun filterActivities(activities: List<Activity>): List<Activity>
}