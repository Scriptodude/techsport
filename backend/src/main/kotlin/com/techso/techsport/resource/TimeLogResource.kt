package com.techso.techsport.resource

import com.techso.techsport.model.response.LogsResponse
import com.techso.techsport.service.TimeLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/log")
@CrossOrigin(value = [
    "http://localhost:4200",
    "http://techsport.herokuapp.com",
    "https://techsport.herokuapp.com"],
    allowCredentials = "true")
class TimeLogResource
@Autowired constructor(private val timeLogService: TimeLogService) {
    @GetMapping
    fun getAll(@RequestParam("page") page: Int): LogsResponse {
        val logs = this.timeLogService.getAll(page)

        return LogsResponse(logs.toList(), logs.totalPages)
    }
}