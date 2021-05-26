package com.techso.techsport.repository

import com.techso.techsport.model.ApplicationConfiguration
import org.springframework.data.repository.CrudRepository

interface ConfigurationRepository : CrudRepository<ApplicationConfiguration, Int> {}