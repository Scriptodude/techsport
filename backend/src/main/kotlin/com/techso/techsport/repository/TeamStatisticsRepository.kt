package com.techso.techsport.repository

import com.techso.techsport.model.statistics.TeamStatistics
import org.springframework.data.repository.CrudRepository

interface TeamStatisticsRepository : CrudRepository<TeamStatistics, String>