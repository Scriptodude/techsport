package com.techso.techsport.resource

import com.techso.techsport.model.request.AddTimeToTeamRequest
import com.techso.techsport.model.request.CreateNewTeamRequest
import com.techso.techsport.model.request.UpdateTeamMembers
import com.techso.techsport.model.response.TimeBasedTeamResponse
import com.techso.techsport.service.LoginService
import com.techso.techsport.service.TeamService
import com.techso.techsport.service.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teams")
@CrossOrigin(value = [
    "http://localhost:4200",
    "http://techsport.herokuapp.com",
    "https://techsport.herokuapp.com"],
    allowCredentials = "true")
class TeamResource
@Autowired constructor(
    private val loginService: LoginService,
    private val teamService: TeamService,
    private val timeService: TimeService
) {
    @GetMapping
    fun getAllTeams() =
        this.teamService
            .getAllTeams()
            .map { TimeBasedTeamResponse(it.name, it.points, it.members, it.pointsChanges) }

    @PostMapping
    fun createNewTeam(
        @CookieValue("token") token: String,
        @RequestBody createNewTeamRequest: CreateNewTeamRequest
    ): TimeBasedTeamResponse {
        this.loginService.validateToken(token)
        val team = this.teamService.createNewTeam(createNewTeamRequest.name, createNewTeamRequest.members)

        return TimeBasedTeamResponse(team.name, team.points, team.members, team.pointsChanges)
    }

    @GetMapping("/{name}")
    fun getTeamByName(@PathVariable name: String): TimeBasedTeamResponse {
        val team = this.teamService.getTeam(name)
        return TimeBasedTeamResponse(team?.name!!, team?.points, team?.members, team?.pointsChanges)
    }

    @PostMapping("/{name}")
    fun addTimeToTeam(
        @PathVariable name: String,
        @CookieValue("token") token: String,
        @RequestBody timeToTeamRequest: AddTimeToTeamRequest
    ) {
        this.loginService.validateToken(token)
        val timeInSeconds = timeService.toSeconds(timeToTeamRequest)
        this.teamService.addPointsToTeam(
            name,
            timeInSeconds,
            timeToTeamRequest)
    }

    @PutMapping("/{name}")
    fun editMembers(
        @PathVariable name: String,
        @CookieValue("token") token: String,
        @RequestBody updateTeamMembers: UpdateTeamMembers): TimeBasedTeamResponse {
        this.loginService.validateToken(token)

        val team = this.teamService.updateTeam(name, updateTeamMembers.members)
        return TimeBasedTeamResponse(team.name, team.points, team.members, team.pointsChanges)
    }
}