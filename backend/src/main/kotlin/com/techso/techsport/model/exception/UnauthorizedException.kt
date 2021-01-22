package com.techso.techsport.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You must login to do this action")
class UnauthorizedException : RuntimeException()