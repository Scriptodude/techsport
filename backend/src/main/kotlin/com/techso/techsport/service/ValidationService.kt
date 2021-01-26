package com.techso.techsport.service

import com.techso.techsport.model.ActivityToValidate
import com.techso.techsport.model.Time
import com.techso.techsport.model.exception.ActivityNotFoundException
import com.techso.techsport.model.exception.AlreadyApprovedException
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.model.strava.response.Athlete
import com.techso.techsport.repository.ActivityToValidateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class ValidationService
@Autowired
constructor(
    private val activityToValidateRepository: ActivityToValidateRepository,
    private val teamService: TeamService
) {

    @Transactional
    fun getAllUnvalidatedActivities() = this.activityToValidateRepository.findAllByApproved(null);

    @Transactional
    fun getAllActivities() = this.activityToValidateRepository.findAll()
        .filter { it.approved != null }
        .sortedBy { it.activityDate }

    @Transactional
    fun changeActivityApprobation(teamName: String, activityId: String, approved: Boolean) {
        val activityOpt = this.activityToValidateRepository.findById(activityId);

        when {
            activityOpt.isEmpty -> {
                throw ActivityNotFoundException()
            }
            activityOpt.get().approved == true -> {
                throw AlreadyApprovedException()
            }
            else -> {
                val activity = activityOpt.get()
                activity.approved = approved

                if (approved) {
                    this.teamService.addTimeToTeam(teamName, activity.activityTime.timeInSeconds)
                }

                this.activityToValidateRepository.save(activity)
            }
        }
    }

    @Transactional
    fun addActivity(activity: Activity, athlete: Athlete) {
        val activityOpt = this.activityToValidateRepository.findById(activity.id.toString());

        if (activityOpt.isPresent) return
        else {
            val newActivity = ActivityToValidate(
                id = activity.id.toString(),
                athleteFullName = athlete.firstname + ' ' + athlete.lastname,
                activityDate = activity.startDate ?: Instant.now(),
                activityTime = Time(activity.movingTime)
            )

            this.activityToValidateRepository.save(newActivity)
        }
    }
}