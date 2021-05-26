package com.techso.techsport.resource

import com.techso.techsport.model.ApplicationConfiguration
import com.techso.techsport.model.request.UpdateConfigurationRequest
import com.techso.techsport.service.ConfigurationService
import com.techso.techsport.service.LoginService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/config")
@CrossOrigin(
    value = [
        "http://localhost:4200",
        "http://techsport.herokuapp.com",
        "https://techsport.herokuapp.com"],
    allowCredentials = "true"
)
class ConfigurationResource(
    private val configurationService: ConfigurationService,
    private val loginService: LoginService
) {

    @GetMapping
    fun getConfig(): ApplicationConfiguration {
        return this.configurationService.getConfig()
    }

    @PutMapping
    fun updateConfig(
        @RequestBody request: UpdateConfigurationRequest,
        @CookieValue("token") token: String
    ): ApplicationConfiguration {
        this.loginService.validateToken(token)
        return this.configurationService.updateConfig(request)
    }
}