package com.techso.techsport.configuration.migration

import com.techso.techsport.model.database.DatabaseMigration
import com.techso.techsport.repository.DatabaseVersionRepository
import org.springframework.stereotype.Service

@Service
class AutomaticMigration(
    private val migrations: List<DatabaseMigration>,
    private val databaseVersionRepository: DatabaseVersionRepository
) {
    init {
        this.migrations.sortedBy { it.version() }.forEach {
            try {
                if (!this.databaseVersionRepository.existsById(it.version().value)) {
                    val success = it.apply()

                    if (success) {
                        this.databaseVersionRepository.save(it.version())
                    }
                }
            } catch (e: Exception) {
                System.err.println("Failed to migrate")
                System.err.println(e)
                e.printStackTrace()
            }
        }
    }
}