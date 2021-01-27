package com.techso.techsport.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.UrlPathHelper

@Configuration
@EnableWebMvc
class WebContent : WebMvcConfigurer {

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer
                .setUseTrailingSlashMatch(true)
                .setPathMatcher(AntPathMatcher())
                .setUrlPathHelper(UrlPathHelper())
                .addPathPrefix(
                        "/api",
                        HandlerTypePredicate.forAnnotation(RestController::class.java))
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/strava")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*")
                .addResourceLocations("classpath:/static/");
    }
}