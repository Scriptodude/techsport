package com.techso.techsport.model

import org.springframework.data.annotation.Id
import java.time.Instant

data class DataImport(
    @Id
    val athleteId: Long,
    var lastImport: Instant
)