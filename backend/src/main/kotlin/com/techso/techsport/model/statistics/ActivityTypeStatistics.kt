package com.techso.techsport.model.statistics

data class ActivityTypeStatistics(
    var count: Int = 0,
    var distance: CommonStatistics = CommonStatistics.build(listOf()),
    var points: CommonStatistics = CommonStatistics.build(listOf()),
    var time: CommonStatistics = CommonStatistics.build(listOf()),
    var uniqueAthleteCount: Int = 0,
)