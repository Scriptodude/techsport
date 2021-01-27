package com.techso.techsport.repository

import com.techso.techsport.model.DataImport
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DataImportRepository : MongoRepository<DataImport, Long>