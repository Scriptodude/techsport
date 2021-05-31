package com.techso.techsport.service.activity

import com.techso.techsport.model.strava.response.Activity
import java.math.BigDecimal

interface PointsCalculator {
    fun calculatePoints(activity: Activity): BigDecimal

    fun getAppliedRate(activity: Activity): BigDecimal
}