package com.techso.techsport.repository

import com.techso.techsport.model.ManualTimeLog
import org.springframework.data.repository.PagingAndSortingRepository

interface ManualTimeLogRepository : PagingAndSortingRepository<ManualTimeLog, String> {}