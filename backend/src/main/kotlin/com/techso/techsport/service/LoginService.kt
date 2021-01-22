package com.techso.techsport.service

import com.techso.techsport.model.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LoginService {
    @Value("#{environment.LOGIN_TOKEN}")
    private lateinit var loginToken: String;

    fun validateToken(token: String) {
        if (token != loginToken) {
            throw UnauthorizedException()
        }
    }
}