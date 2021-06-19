package com.techso.techsport.model.statistics

import java.math.BigDecimal

class CommonStatistics(private val values: List<BigDecimal>) {
    var min: BigDecimal
    var max: BigDecimal
    var average: BigDecimal
    var total: BigDecimal
    var count: Int = values.size

    init {
        this.min = this.values.minOrNull()?.setScale(2) ?: BigDecimal.ZERO
        this.max = this.values.maxOrNull()?.setScale(2) ?: BigDecimal.ZERO
        this.total = this.values.reduce { acc, bigDecimal -> acc.add(bigDecimal) }.setScale(2)
        this.average = this.total
            .divide(this.count.toBigDecimal()).setScale(2)
    }
}
