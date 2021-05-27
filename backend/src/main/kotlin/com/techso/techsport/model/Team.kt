package com.techso.techsport.model

import org.springframework.data.annotation.Id
import java.time.LocalDate
import kotlin.math.max

open class Team(@Id val name: String) {
    var members: MutableList<String> = mutableListOf()
        private set;
    var points: Double = 0.0
        private set;
    var pointsChanges: MutableMap<LocalDate, Double> = mutableMapOf()
        private set;

    fun addTime(points: Double) {
        this.points += points
        this.points = max(this.points, 0.0);

        val oldValue = this.pointsChanges.getOrDefault(LocalDate.now(), 0.0);
        this.pointsChanges[LocalDate.now()] = oldValue + points
    }
}