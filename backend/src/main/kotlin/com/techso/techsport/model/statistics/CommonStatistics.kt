package com.techso.techsport.model.statistics

import java.math.BigDecimal

data class CommonStatistics(
    var min: BigDecimal,
    var max: BigDecimal,
    var average: BigDecimal,
    var total: BigDecimal
) {
    companion object {
        fun build(values: List<BigDecimal>): CommonStatistics {
            val total = values.reduce(BigDecimal::add)
            return CommonStatistics(
                min = (values.minOrNull() ?: BigDecimal.ZERO).setScale(2),
                max = (values.maxOrNull() ?: BigDecimal.ZERO).setScale(2),
                total = total,
                average = (total.divide(values.size.toBigDecimal())),
            )
        }
    }
}
