package com.techso.techsport.resource

import com.techso.techsport.model.ActivityToValidate
import com.techso.techsport.model.request.ChangeActivityApprobation
import com.techso.techsport.service.LoginService
import com.techso.techsport.service.ValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activities")
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
    @GetMapping("/to-validate")
    fun getActivitiesToValidate(
        @CookieValue("token") token: String
    ): List<ActivityToValidate> {
        this.loginService.validateToken(token)

        return this.validationService.getAllUnvalidatedActivities()
    }

    @GetMapping
    fun getAllActivities(
        @CookieValue("token") token: String
    ): List<ActivityToValidate> {
        this.loginService.validateToken(token)

        return this.validationService.getAllActivities()
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