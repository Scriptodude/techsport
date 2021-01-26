package com.techso.techsport.repository

import com.techso.techsport.model.ActivityToValidate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityToValidateRepository : MongoRepository<ActivityToValidate, String> {
    fun findAllByApproved(approved: Boolean?): List<ActivityToValidate>
}