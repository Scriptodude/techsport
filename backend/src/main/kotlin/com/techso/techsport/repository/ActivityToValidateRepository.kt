package com.techso.techsport.repository

import com.techso.techsport.model.ActivityToValidate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityToValidateRepository : PagingAndSortingRepository<ActivityToValidate, String> {
    fun findAllByApproved(approved: Boolean?, pageable: Pageable): Page<ActivityToValidate>

    fun findAllByTeamNameAndApproved(teamName: String?, approved: Boolean?, pageable: Pageable): Page<ActivityToValidate>
    fun findAllByTeamName(teamName: String?, pageable: Pageable): Page<ActivityToValidate>
    fun findAllByTeamName(teamName: String?): List<ActivityToValidate>

    fun findAllByAthleteId(id: Long?, pageable: Pageable): Page<ActivityToValidate>
}