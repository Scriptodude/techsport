package com.techso.techsport.model

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.math.max

open class Team(@Id val name: String) {
    var members: MutableList<String> = mutableListOf()
        private set;
    var points: BigDecimal = BigDecimal.ZERO
        private set;
    var pointsChanges: MutableMap<LocalDate, BigDecimal> = mutableMapOf()
        private set;

    fun addTime(points: BigDecimal) {
        this.points.add(points)
        this.points.max(BigDecimal.ZERO);

        val oldValue = this.pointsChanges.getOrDefault(LocalDate.now(), BigDecimal.ZERO);
        this.pointsChanges[LocalDate.now()] = oldValue.add(points)
    }
}