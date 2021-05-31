package com.techso.techsport.service

import com.techso.techsport.model.ActivityToValidate
import com.techso.techsport.model.Time
import com.techso.techsport.model.exception.ActivityNotFoundException
import com.techso.techsport.model.exception.AlreadyApprovedException
import com.techso.techsport.model.strava.response.Activity
import com.techso.techsport.model.strava.response.Athlete
import com.techso.techsport.repository.ActivityToValidateRepository
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

@Service
class ValidationService
@Autowired
constructor(
    private val activityToValidateRepository: ActivityToValidateRepository,
    private val teamService: TeamService,
    private val activityManagerProvider: ActivityManagerProvider
) {

    @Transactional
    fun getAllActivities(page: Int, approved: Boolean?): Page<ActivityToValidate> =
        this.activityToValidateRepository
            .findAllByApproved(approved, PageRequest.of(page, 10, Sort.by("id").descending()))

    @Transactional
    fun getAllActivities(page: Int): Page<ActivityToValidate> =
        this.activityToValidateRepository
            .findAll(PageRequest.of(page, 10, Sort.by("id").descending()))

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
                    this.teamService.addPointsToTeam(
                        teamName,
                        activity.points ?: activity.activityTime.timeInSeconds.toBigDecimal()
                    )
                    activity.teamName = teamName
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