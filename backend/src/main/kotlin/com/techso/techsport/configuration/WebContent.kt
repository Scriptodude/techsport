package com.techso.techsport.configuration

import org.springframework.boot.web.server.ErrorPage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.util.UrlPathHelper
import org.springframework.http.HttpStatus
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory

import org.springframework.boot.web.server.WebServerFactoryCustomizer

@Configuration
class WebContent : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/notFound").setViewName("forward:/")
    }

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
        registry.addResourceHandler("/*")
            .addResourceLocations("classpath:/static/*");
        registry.addResourceHandler("/strava")
            .addResourceLocations("classpath:/static/*");
        registry.addResourceHandler("/admin")
            .addResourceLocations("classpath:/static/*");
    }

    @Bean
    fun containerCustomizer(): WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>? {
        return WebServerFactoryCustomizer { container: ConfigurableServletWebServerFactory ->
            container.addErrorPages(
                ErrorPage(
                    HttpStatus.NOT_FOUND,
                    "/notFound"
                )
            )
        }
    }
}