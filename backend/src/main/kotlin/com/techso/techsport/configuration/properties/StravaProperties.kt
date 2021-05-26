package com.techso.techsport.configuration.properties

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class StravaProperties(
    val url: StravaUrlProperties,
    val client: StravaClientProperties
)