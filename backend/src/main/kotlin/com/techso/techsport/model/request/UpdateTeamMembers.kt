package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator

data class UpdateTeamMembers
@JsonCreator
constructor(val members: List<String>)