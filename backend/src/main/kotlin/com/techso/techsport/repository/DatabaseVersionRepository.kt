package com.techso.techsport.repository

import com.techso.techsport.model.database.DatabaseVersion
import org.springframework.data.repository.CrudRepository

interface DatabaseVersionRepository : CrudRepository<DatabaseVersion, Int>