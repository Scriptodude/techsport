package com.techso.techsport.service

import com.techso.techsport.model.request.AddTimeToTeamRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TimeServiceTest {

    @Test
    fun convertsToSecondsCorrectly() {
        val service = TimeService()

        assertThat(service.toSeconds(AddTimeToTeamRequest(1, 5, 2))).isEqualTo(3600 + 60 * 5 + 2)
    }

    @Test
    fun convertsToSecondsCorrectly2() {
        val service = TimeService()

        assertThat(service.toSeconds(AddTimeToTeamRequest(5, 0, null))).isEqualTo(3600 * 5)
    }

    @Test
    fun convertsToSecondsCorrectly3() {
        val service = TimeService()

        assertThat(service.toSeconds(AddTimeToTeamRequest(0, 2, null))).isEqualTo(60 * 2)
    }

    @Test
    fun convertsToSecondsCorrectly4() {
        val service = TimeService()

        assertThat(service.toSeconds(AddTimeToTeamRequest(0, 0, null))).isEqualTo(0)
    }

    @Test
    fun convertsToSecondsCorrectly5() {
        val service = TimeService()

        assertThat(service.toSeconds(AddTimeToTeamRequest(0, 0, 1))).isEqualTo(1)
    }
}