package com.techso.techsport.model.statistics

data class ActivityTypeStatistics(
    var count: Int = 0,
    var distance: CommonStatistics = CommonStatistics(listOf()),
    var points: CommonStatistics = CommonStatistics(listOf()),
    var time: CommonStatistics = CommonStatistics(listOf()),
    var uniqueAthleteCount: Int = 0,
)