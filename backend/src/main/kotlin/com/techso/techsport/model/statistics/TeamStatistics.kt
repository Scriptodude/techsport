package com.techso.techsport.model.statistics

import com.techso.techsport.model.strava.ActivityType
import org.springframework.data.annotation.Id

data class TeamStatistics (
    @Id
    var teamName: String,
    var totalActivities: Int,
    var statsPerType: Map<ActivityType, ActivityTypeStatistics>,
    var totalAthletes: Int,
)