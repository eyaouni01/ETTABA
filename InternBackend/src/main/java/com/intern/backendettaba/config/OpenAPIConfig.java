package com.intern.backendettaba.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Rami",
                        email = "rami.benhamouda@esprit.tn"
                ),
                description = "OpenAPIDefinition for Spring"
        )
)
public class OpenAPIConfig {
}
