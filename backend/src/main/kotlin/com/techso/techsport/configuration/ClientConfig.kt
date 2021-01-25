package com.techso.techsport.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.techso.techsport.client.StravaClient
import com.techso.techsport.model.StravaConfig
import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig(
    @Value("\${techsport.strava.api.url}") val stravaUrl: String,
    @Value("\${techsport.strava.client.id}") val stravaClientId: Int,
    @Value("\${techsport.strava.client.secret}") val stravaClientSecret: String,
    @Value("\${techsport.strava.oauth.url}") val stravaOauthUrl: String,
    @Value("\${techsport.api.url}") val redirectUrl: String,
) {

    @Bean
    fun objectMapper() =
        ObjectMapper()
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean
    fun stravaAuthClient(objectMapper: ObjectMapper) =
        Feign.builder()
            .encoder(JacksonEncoder(objectMapper))
            .decoder(JacksonDecoder(objectMapper))
            .client(OkHttpClient())
            .target(StravaClient::class.java, this.stravaUrl);

    @Bean
    fun stravaClientConfig() =
        StravaConfig(
            this.stravaClientId,
            this.stravaClientSecret,
            this.stravaOauthUrl,
            this.redirectUrl
        )
}