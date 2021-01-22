package com.techso.techsport

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.techso.techsport.**"],
	exclude = [SecurityAutoConfiguration::class, EmbeddedMongoAutoConfiguration::class])
class TechsportsApplication

fun main(args: Array<String>) {
	runApplication<TechsportsApplication>(*args)
}
