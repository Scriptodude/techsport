package com.techso.techsport.resource

import com.techso.techsport.model.ApplicationConfiguration
import com.techso.techsport.model.request.UpdateConfigurationRequest
import com.techso.techsport.model.response.ConfigurationResponse
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
    fun getConfig(@RequestParam("lang", defaultValue = "fr") language: String): ConfigurationResponse {
        return ConfigurationResponse(this.configurationService.getConfig(), language)
    }

    @PutMapping
    fun updateConfig(
        @RequestBody request: UpdateConfigurationRequest,
        @RequestParam("lang", defaultValue = "fr") language: String,
        @CookieValue("token") token: String
    ): ConfigurationResponse {
        this.loginService.validateToken(token)
        return ConfigurationResponse(this.configurationService.updateConfig(request), language)
    }
}