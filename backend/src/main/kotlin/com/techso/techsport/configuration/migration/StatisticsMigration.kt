package com.techso.techsport.configuration.migration

import com.techso.techsport.model.database.DatabaseMigration
import com.techso.techsport.model.database.DatabaseVersion
import com.techso.techsport.repository.TeamRepository
import com.techso.techsport.service.TeamStatisticsService
import org.springframework.stereotype.Component

@Component
class StatisticsMigration(
    private val teamRepository: TeamRepository,
    private val teamStatisticsService: TeamStatisticsService
) :
    DatabaseMigration() {
    override fun version(): DatabaseVersion = DatabaseVersion(1)

    override fun apply(): Boolean {
        return try {
            val teams = this.teamRepository.findAll();
            teams.map { it.name }.forEach(this.teamStatisticsService::updateTeamStatFromScratch)

            true
        } catch (e: Exception) {
            System.err.println(e);
            false;
        }
    }
}