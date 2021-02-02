package com.techso.techsport.resource

import com.techso.techsport.model.request.ChangeActivityApprobation
import com.techso.techsport.model.response.AllActivitiesResponse
import com.techso.techsport.service.LoginService
import com.techso.techsport.service.ValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/activities")
@CrossOrigin(
    value = [
        "http://localhost:4200",
        "http://techsport.herokuapp.com",
        "https://techsport.herokuapp.com"],
    allowCredentials = "true"
)
class ActivityResource
@Autowired
constructor(
    private val validationService: ValidationService,
    private val loginService: LoginService
) {
    @GetMapping
    fun getAllActivities(
        @CookieValue("token") token: String,
        @RequestParam("page") page: Int,
        @RequestParam("approved", required = false) approved: Boolean?,
        @RequestParam("all", required = false) all: Boolean?
    ): AllActivitiesResponse {
        this.loginService.validateToken(token)

        val activities = when (all) {
            true -> this.validationService.getAllActivities(page)
            else -> this.validationService.getAllActivities(page, approved)
        }
        val pages = activities.totalPages

        return AllActivitiesResponse(activities.toList(), pages)
    }

    @PostMapping
    fun changeActivityApprobation(
        @CookieValue("token") token: String,
        @RequestBody req: ChangeActivityApprobation
    ) {
        this.loginService.validateToken(token)
        this.validationService.changeActivityApprobation(req.teamName ?: "", req.activityId, req.approved)
    }
}