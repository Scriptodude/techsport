package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator


data class AddTimeToTeamRequest
@JsonCreator
constructor(
    val hours: Int,
    val minutes: Int,
    val seconds: Int?,
    val who: String,
    val why: String,
    val athlete: String)