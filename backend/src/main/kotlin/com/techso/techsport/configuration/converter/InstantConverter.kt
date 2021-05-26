package com.techso.techsport.configuration.converter

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
@ConfigurationPropertiesBinding
class InstantConverter : Converter<String, Instant> {
    override fun convert(source: String): Instant? {
        return Optional.ofNullable(source).map(Instant::parse).orElse(null);
    }
}