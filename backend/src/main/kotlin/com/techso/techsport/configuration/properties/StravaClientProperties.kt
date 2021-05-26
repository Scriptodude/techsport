package com.techso.techsport.configuration.properties

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class StravaClientProperties(
    val id: Int,
    val secret: String
)