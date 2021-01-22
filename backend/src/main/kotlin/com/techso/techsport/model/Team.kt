package com.techso.techsport.model

import org.springframework.data.annotation.Id
import java.time.LocalDate
import kotlin.math.max

open class Team(@Id val name: String) {
    var members: MutableList<String> = mutableListOf()
        private set;
    var timeInSeconds: Long = 0
        private set;
    var timeChanges: MutableMap<LocalDate, Long> = mutableMapOf()
        private set;

    fun addTime(seconds: Long) {
        this.timeInSeconds += seconds
        this.timeInSeconds = max(this.timeInSeconds, 0L);

        val oldValue = this.timeChanges.getOrDefault(LocalDate.now(), 0);
        this.timeChanges[LocalDate.now()] = oldValue + seconds
    }
}