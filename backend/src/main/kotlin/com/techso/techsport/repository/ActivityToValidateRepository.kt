package com.techso.techsport.repository

import com.techso.techsport.model.ActivityToValidate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityToValidateRepository : PagingAndSortingRepository<ActivityToValidate, String> {
    fun findAllByApproved(approved: Boolean?, pageable: Pageable): Page<ActivityToValidate>
}