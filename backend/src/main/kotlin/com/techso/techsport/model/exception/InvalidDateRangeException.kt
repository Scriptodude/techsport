package com.techso.techsport.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The start time must be before the end time")
class InvalidDateRangeException : RuntimeException()