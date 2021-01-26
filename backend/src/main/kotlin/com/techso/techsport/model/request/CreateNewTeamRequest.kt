package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator

class CreateNewTeamRequest
@JsonCreator
constructor(val name: String, val members: List<String>)