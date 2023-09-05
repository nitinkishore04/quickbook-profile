package com.business.profile.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {
    @Bean
    fun apiInfo(): OpenAPI {
        return OpenAPI().info(
            Info().title("Test API documentation")
                .description("Documentation for Test weather API")
                .version("v1")
                .contact(contactDetails)
                .license(licenseDetails)
        )
    }

    private val contactDetails: Contact
        get() = Contact().name("Contact Person").email("nitin.kishore04@email.con").url("https://website.com")
    private val licenseDetails: License
        get() = License().name("License name").url("https://license-url.com")
}