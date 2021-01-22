package com.techso.techsport.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The resource already exists")
class AlreadyExistsException : RuntimeException()