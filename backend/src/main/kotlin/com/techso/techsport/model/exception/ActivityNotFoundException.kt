package com.techso.techsport.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "The activity was not found")
class ActivityNotFoundException : RuntimeException() {
}