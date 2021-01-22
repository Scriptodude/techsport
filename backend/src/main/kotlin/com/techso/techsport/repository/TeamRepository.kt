package com.techso.techsport.repository

import com.techso.techsport.model.Team
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : MongoRepository<Team, String> {
    fun findByName(name: String): Team?
}