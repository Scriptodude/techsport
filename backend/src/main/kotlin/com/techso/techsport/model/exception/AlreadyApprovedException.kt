package com.techso.techsport.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.CONFLICT, reason = "The activity was already approved or denied")
class AlreadyApprovedException : RuntimeException()