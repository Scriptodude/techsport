package com.techso.techsport

import com.techso.techsport.configuration.converter.InstantConverter
import com.techso.techsport.configuration.properties.TechsportProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.techso.techsport.**"],
    exclude = [SecurityAutoConfiguration::class]
)
@EnableConfigurationProperties(value = [TechsportProperties::class])
class TechsportsApplication

fun main(args: Array<String>) {
    runApplication<TechsportsApplication>(*args)
}
