package com.techso.techsport.service

import com.techso.techsport.model.ManualTimeLog
import com.techso.techsport.model.Time
import com.techso.techsport.repository.ManualTimeLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class PointsLogService
@Autowired
constructor(private val manualTimeLogRepository: ManualTimeLogRepository) {
    @Transactional
    fun log(whoEntered: String, why: String, athlete: String, team: String, timeInSeconds: Long) {
        val log = ManualTimeLog(
            id = UUID.randomUUID().toString(),
            whoEntered = whoEntered,
            teamName = team,
            why = why,
            athleteName = athlete,
            dateTime = Instant.now(),
            time = Time(timeInSeconds)
        )

        this.manualTimeLogRepository.save(log)
    }

    @Transactional
    fun getAll(page: Int) =
        this.manualTimeLogRepository
            .findAll(PageRequest.of(page, 10, Sort.by("dateTime").descending()))
}