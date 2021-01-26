package com.techso.techsport.model.request

import com.fasterxml.jackson.annotation.JsonCreator

data class LoginRequest
@JsonCreator
constructor(val token: String)