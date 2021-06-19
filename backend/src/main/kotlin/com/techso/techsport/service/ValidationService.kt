package com.techso.techsport.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.techso.techsport.model.ActivityToValidate
import com.techso.techsport.model.Time
import com.techso.techsport.model.exception.ActivityNotFoundException
import com.techso.techsport.model.exception.AlreadyApprovedException
import com.techso.techsport.model.exception.TeamNotFoundException
import com.techso.techsport.model.strava.ActivityType
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.model.strava.response.Athlete
import com.techso.techsport.repository.ActivityToValidateRepository
import com.techso.techsport.repository.TeamStatisticsRepository
import com.techso.techsport.service.activity.ActivityManagerProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import javax.servlet.http.HttpSession

@Service
class ValidationService
@Autowired
constructor(
    private val activityToValidateRepository: ActivityToValidateRepository,
    private val teamStatisticsRepository: TeamStatisticsRepository,
    private val teamService: TeamService,
    private val activityManagerProvider: ActivityManagerProvider,
    private val objectMapper: ObjectMapper
) {

    @Transactional
    fun getAllActivities(
        page: Int,
        approved: Boolean?,
        teamName: String?
    ): Page<ActivityToValidate> =
        if (teamName == null) {
            this.activityToValidateRepository
                .findAllByApproved(approved, PageRequest.of(page, 10, Sort.by("id").descending()))
        } else {
            this.activityToValidateRepository.findAllByTeamNameAndApproved(
                teamName,
                approved,
                PageRequest.of(page, 10, Sort.by("id").descending())
            )
        }

    @Transactional
    fun getAllActivities(page: Int, teamName: String?): Page<ActivityToValidate> =
        if (teamName == null) {
            this.activityToValidateRepository
                .findAll(PageRequest.of(page, 10, Sort.by("id").descending()))
        } else {
            this.activityToValidateRepository.findAllByTeamName(
                teamName,
                PageRequest.of(page, 10, Sort.by("id").descending())
            )
        }

    @Transactional
    fun getPointsPerActivityType(teamName: String): Map<ActivityType?, BigDecimal?> {
        val activities =
            this.teamStatisticsRepository.findById(teamName).orElseThrow { TeamNotFoundException() }

        return activities
            .statsPerType
            .map { Pair(it.key, it.value.points.total) }
            .associate { it }
    }

    @Transactional
    fun getActivitiesOfUser(session: HttpSession, page: Int): Page<ActivityToValidate> =
        if (session.getAttribute("athlete") == null) {
            Page.empty()
        } else {
            val athlete = this.objectMapper.readValue(
                session.getAttribute("athlete") as String,
                Athlete::class.java
            )
            this.activityToValidateRepository.findAllByAthleteFullName(
                athlete.firstname + ' ' + athlete.lastname,
                PageRequest.of(page, 10, Sort.by("id").descending())
            )
        }

    @Transactional
    fun changeActivityApprobation(teamName: String, activityId: String, approved: Boolean) {
        val activity = this.activityToValidateRepository.findById(activityId)
            .orElseThrow { ActivityNotFoundException() }

        when {

            // The activity was already approved for a team
            activity.approved == true -> {

                // And we want to re-approve it
                if (approved) {

                    // If the team did not change, then we do nothing
                    if (teamName == activity.teamName) {
                        throw AlreadyApprovedException()
                    } else {

                        // Otherwise, the team changed, we roll back the points to the previous team
                        // And add them to the new team
                        this.teamService.rollbackPoints(activity.teamName ?: "", activity)
                        this.teamService.addPointsToTeam(teamName, activity)
                        activity.teamName = teamName
                    }
                } else {

                    // We want to remove the points of the team and bring it back to approved
                    this.teamService.rollbackPoints(teamName, activity)
                    activity.approved = null;
                    activity.teamName = null;
                }
            }
            else -> {

                // The activity was previously unapproved, simply change the approbation
                activity.approved = approved

                if (approved) {
                    this.teamService.addPointsToTeam(teamName, activity)
                    activity.teamName = teamName
                }
            }
        }

        this.activityToValidateRepository.save(activity)
    }

    @Transactional
    fun addActivity(activity: Activity, athlete: Athlete) {
        val activityOpt = this.activityToValidateRepository.findById(activity.id.toString());

        if (activityOpt.isPresent) return
        else {
            val newActivity = ActivityToValidate(
                id = activity.id.toString(),
                athleteId = athlete.id,
                athleteFullName = athlete.firstname + ' ' + athlete.lastname,
                activityDate = activity.startDate ?: Instant.now(),
                activityTime = Time(activity.movingTime),
                distance = BigDecimal.valueOf(activity.distance).setScale(2, RoundingMode.HALF_UP),
                type = activity.type,
                isManual = activity.isManual,
                points = this.activityManagerProvider.calculatePoints(activity),
                appliedRate = this.activityManagerProvider.getAppliedRate(activity)
            )

            this.activityToValidateRepository.save(newActivity)
        }
    }
}