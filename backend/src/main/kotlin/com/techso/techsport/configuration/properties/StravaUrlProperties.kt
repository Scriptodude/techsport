package com.techso.techsport.configuration.properties

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class StravaUrlProperties(val api: String, val oauth: String, val redirect: String)
