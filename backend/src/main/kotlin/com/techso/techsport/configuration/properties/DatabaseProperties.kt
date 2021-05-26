package com.techso.techsport.configuration.properties

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class DatabaseProperties(
    val name: String,
    val host: String,
    val user: String,
    val password: String
)