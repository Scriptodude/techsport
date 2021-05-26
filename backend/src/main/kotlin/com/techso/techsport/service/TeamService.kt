package com.techso.techsport.service

import com.techso.techsport.model.Team
import com.techso.techsport.model.exception.AlreadyExistsException
import com.techso.techsport.model.exception.TeamNotFoundException
import com.techso.techsport.model.request.AddTimeToTeamRequest
import com.techso.techsport.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService
@Autowired constructor(
    private val teamRepository: TeamRepository,
    private val timeLogService: TimeLogService
) {
    @Transactional
    fun getAllTeams(): List<Team> =
        teamRepository.findAll(Sort.by(Sort.Direction.DESC, "timeInSeconds"));

    @Transactional
    fun getTeam(name: String) = teamRepository.findByName(name)

    @Transactional
    fun addPointsToTeam(name: String, timeInSeconds: Long, timeToTeamRequest: AddTimeToTeamRequest) {
        this.addPointsToTeam(name, timeInSeconds)
        this.timeLogService.log(
            timeToTeamRequest.who,
            timeToTeamRequest.why,
            timeToTeamRequest.athlete,
            name,
            timeInSeconds
        )
    }

    @Transactional
    fun createNewTeam(name: String, members: List<String>): Team {
        if (teamRepository.findByName(name) != null) {
            throw AlreadyExistsException()
        }

        val team = Team(name)
        team.members.addAll(members)

        return teamRepository.save(team)
    }

    @Transactional
    fun updateTeam(name: String, members: List<String>): Team {
        val team = teamRepository.findByName(name) ?: throw TeamNotFoundException()

        team.members.clear()
        team.members.addAll(members)

        return teamRepository.save(team)
    }

    @Transactional
    fun addPointsToTeam(teamName: String, points: Long) {
        val team = teamRepository.findByName(teamName)

        if (team != null) {
            team.addTime(points)
            teamRepository.save(team)
        } else {
            throw TeamNotFoundException()
        }
    }
}