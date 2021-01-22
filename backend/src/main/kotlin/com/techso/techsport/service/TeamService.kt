package com.techso.techsport.service

import com.techso.techsport.model.Team
import com.techso.techsport.model.exception.AlreadyExistsException
import com.techso.techsport.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService
@Autowired constructor(private val teamRepository: TeamRepository) {
    @Transactional
    fun getAllTeams(): List<Team> =
        teamRepository.findAll(Sort.by(Sort.Direction.DESC, "timeInSeconds"));

    @Transactional
    fun getTeam(name: String) = teamRepository.findByName(name)

    @Transactional
    fun addTimeToTeam(name: String, timeInSeconds: Long) {
        val team = teamRepository.findByName(name)

        if (team != null) {
            team.addTime(timeInSeconds)
            teamRepository.save(team)
        }
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
}