package com.techso.techsport.resource

import com.techso.techsport.model.request.LoginRequest
import com.techso.techsport.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin(value = [
    "http://localhost:4200",
    "http://techsport.herokuapp.com",
    "https://techsport.herokuapp.com"],
    allowCredentials = "true")
class LoginResource
@Autowired constructor(private val loginService: LoginService) {

    @PostMapping("/isAdmin")
    fun isAdmin(@CookieValue("token") token: String) {
        this.loginService.validateToken(token)
    }
}