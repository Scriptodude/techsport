package com.techso.techsport.service

import com.techso.techsport.model.statistics.ActivityTypeStatistics
import com.techso.techsport.model.statistics.CommonStatistics
import com.techso.techsport.model.statistics.TeamStatistics
import com.techso.techsport.repository.ActivityToValidateRepository
import com.techso.techsport.repository.TeamStatisticsRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TeamStatisticsService(
    private val activityToValidateRepository: ActivityToValidateRepository,
    val teamStatisticsRepository: TeamStatisticsRepository
) {

    fun updateTeamStatFromScratch(name: String) {
        try {
            val activities = this.activityToValidateRepository.findAllByTeamName(name)

            val groupedActivities =
                activities
                    .filter { it.type != null }
                    .groupBy { it.type!! }

            val statisticsPerType = groupedActivities
                .map { entry ->
                    val typeStatistics = ActivityTypeStatistics(
                        count = entry.value.size,
                        distance = CommonStatistics.build(entry.value.map {
                            it.distance ?: BigDecimal.ZERO
                        }),
                        points = CommonStatistics.build(entry.value.map {
                            it.points ?: BigDecimal.ZERO
                        }),
                        time = CommonStatistics.build(entry.value.map { it.activityTime.timeInSeconds.toBigDecimal() }),
                        uniqueAthleteCount = entry.value.distinctBy { it.athleteFullName }.size
                    )

                    Pair(entry.key, typeStatistics)
                }.associate { it }

            val stats = TeamStatistics(
                teamName = name,
                totalActivities = activities.size,
                statsPerType = statisticsPerType,
                totalAthletes = activities.distinctBy { it.athleteFullName }.size
            )

            this.teamStatisticsRepository.save(stats)
        } catch (e: Exception) {
            System.err.println(e)
        }
    }
}