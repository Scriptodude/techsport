package com.techso.techsport.service

import com.techso.techsport.model.ActivityToValidate
import com.techso.techsport.model.Team
import com.techso.techsport.model.exception.AlreadyExistsException
import com.techso.techsport.model.exception.TeamNotFoundException
import com.techso.techsport.model.request.AddTimeToTeamRequest
import com.techso.techsport.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class TeamService
@Autowired constructor(
    private val teamRepository: TeamRepository,
    private val timeLogService: PointsLogService
) {
    @Transactional
    fun getAllTeams(): List<Team> =
        teamRepository.findAll(Sort.by(Sort.Direction.DESC, "timeInSeconds"));

    @Transactional
    fun getTeam(name: String) =
        teamRepository.findByName(name).orElseThrow { TeamNotFoundException() }

    @Transactional
    fun addPointsToTeam(
        name: String,
        timeInSeconds: Double,
        timeToTeamRequest: AddTimeToTeamRequest
    ) {
        this.addPointsToTeam(name, BigDecimal.valueOf(timeInSeconds))
        this.timeLogService.log(
            timeToTeamRequest.who,
            timeToTeamRequest.why,
            timeToTeamRequest.athlete,
            name,
            timeInSeconds.toLong()
        )
    }

    @Transactional
    fun createNewTeam(name: String, members: List<String>): Team {
        if (teamRepository.findByName(name).isPresent) {
            throw AlreadyExistsException()
        }

        val team = Team(name)
        team.members.addAll(members)
        team.addPoints(BigDecimal.ZERO)

        return teamRepository.save(team)
    }

    @Transactional
    fun updateTeam(name: String, members: List<String>): Team {
        val team = this.getTeam(name)

        team.members.clear()
        team.members.addAll(members)

        return teamRepository.save(team)
    }

    @Transactional
    fun addPointsToTeam(teamName: String, points: BigDecimal) {
        val team = this.getTeam(teamName)
        team.addPoints(points)
        teamRepository.save(team)
    }

    @Transactional
    fun addPointsToTeam(teamName: String, activity: ActivityToValidate) {
        return this.addPointsToTeam(
            teamName,
            activity.points ?: activity.activityTime.timeInSeconds.toBigDecimal()
        )
    }

    @Transactional
    fun rollbackPoints(teamName: String, activity: ActivityToValidate) {
        this.addPointsToTeam(
            teamName,
            (activity.points ?: activity.activityTime.timeInSeconds.toBigDecimal()).negate()
        )
    }
}