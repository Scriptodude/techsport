package com.techso.techsport.service

import com.techso.techsport.model.ApplicationConfiguration
import com.techso.techsport.model.request.UpdateConfigurationRequest
import com.techso.techsport.repository.ConfigurationRepository
import org.springframework.stereotype.Service

@Service
class ConfigurationService(private val configurationRepository: ConfigurationRepository) {
    fun getConfig(): ApplicationConfiguration {
        return this.configurationRepository.findById(1).orElseGet {
            this.configurationRepository.save(ApplicationConfiguration())
        }
    }

    fun updateConfig(request: UpdateConfigurationRequest): ApplicationConfiguration {
        return this.configurationRepository.save(
            ApplicationConfiguration(
                appMode = request.mode,
                pointModifiers = request.modifiers,
                startDate = request.startDate,
                endDate = request.endDate
            )
        )
    }
}