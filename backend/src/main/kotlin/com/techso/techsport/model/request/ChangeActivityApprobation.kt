package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator

data class ChangeActivityApprobation
@JsonCreator
constructor(
    val activityId: String,
    val approved: Boolean,
    val teamName: String?
)
