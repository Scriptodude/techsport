package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity

class DistancePerSportActivityFiltering : ActivityFiltering {
    override fun filterActivities(activities: List<Activity>) = activities;
}